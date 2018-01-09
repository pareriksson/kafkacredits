package se.perrz.stream;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.perrz.Const;
import se.perrz.model.event.ApplicationRejectedEvent;
import se.perrz.model.event.RequestCreditReportEvent;
import se.perrz.model.internal.AnoLoanApplication;
import se.perrz.model.loan.Applicant;
import se.perrz.model.loan.LoanApplication;
import se.perrz.model.util.PersonKey;
import se.perrz.stream.serde.JsonSerde;
import se.perrz.stream.serde.KafkaStreamTypeValidator;

import java.util.Properties;

@Component
public class KnockOutProcessor {

  private static final Integer AGE_LIMIT = 50;
  Logger log = LoggerFactory.getLogger(KnockOutProcessor.class);

  /**
   * Sätter upp config för denna stream processor.
   */
  public KnockOutProcessor() {

    log.info("Starting KnockOutProcessor");
    /////////////////
    // Konfiguration av strömmen
    /////////////////
    StreamsBuilder builder = new StreamsBuilder();

    KStream<String, AnoLoanApplication> applicationKStream = KafkaStreamTypeValidator
        .validatedStream(builder, Const.ANO_LA_TOPIC, Serdes.String(), JsonSerde.from(AnoLoanApplication.class));

    // TODO Ongoing application check

    KStream<String, AnoLoanApplication>[] branched = applicationKStream.branch(
        (key, val) -> val.getPersonAge() < AGE_LIMIT,
        (key, val) -> val.getPersonAge() >= AGE_LIMIT);

    KStream<String, AnoLoanApplication> rejectedApplications = branched[0];
    KStream<String, AnoLoanApplication> okApplications = branched[1];


    // Ok flöde
    okApplications
        .foreach((key, value) -> log.info("Generating a credit report event for id: {}", key));

    okApplications
        // Generera ett RequestCreditReportEvent
        .map((key, value) -> KeyValue.pair(value.getPersonKey(), RequestCreditReportEvent.forPersonKey(value.getPersonKey())))

        .to(Const.REQ_CREDIT_REPORT_TOPIC,
            Produced.with(Serdes.String(), JsonSerde.from(RequestCreditReportEvent.class)));


    // rejected flöde
    rejectedApplications
        // Generera ett RequestCreditReportEvent
        .map((key, value) -> KeyValue.pair(key, ApplicationRejectedEvent.forId(key)))

        .to(Const.LA_RESP_TOPIC,
            Produced.with(Serdes.String(), JsonSerde.from(ApplicationRejectedEvent.class)));



    /////////////////
    // Starta processen
    /////////////////
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KnockOutProcessor");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    new KafkaStreamsBuilder(
        new StreamsConfig(props),
        builder,
        0,
        false,
        null);

    log.info("Started KnockOutProcessor DONE");
  }
}
