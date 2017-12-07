package se.perrz.stream;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.perrz.Const;
import se.perrz.model.event.RequestCreditReportEvent;
import se.perrz.model.ext.CreditReport;
import se.perrz.model.internal.AnoLoanApplication;
import se.perrz.model.loan.Applicant;
import se.perrz.model.loan.LoanApplication;
import se.perrz.model.util.PersonKey;
import se.perrz.service.CreditReportService;
import se.perrz.stream.serde.JsonSerde;
import se.perrz.stream.serde.KafkaStreamTypeValidator;

import java.util.Properties;

@Component
public class CreditReportFetcher {

  Logger log = LoggerFactory.getLogger(CreditReportFetcher.class);

  @Autowired CreditReportService creditReportService;

  /**
   * Sätter upp config för denna stream processor.
   */
  public CreditReportFetcher() {

    log.info("Starting CreditReportFetcher");
    /////////////////
    // Konfiguration av strömmen
    /////////////////
    StreamsBuilder builder = new StreamsBuilder();

    KStream<String, RequestCreditReportEvent> creditEvent = KafkaStreamTypeValidator
        .validatedStream(builder, Const.REQ_CREDIT_REPORT_TOPIC, Serdes.String(), JsonSerde.from(RequestCreditReportEvent.class));

    creditEvent

        // Bygg om till ny ström för låneflödet
        .map((key, value) -> KeyValue.pair(value.getPersonKey(), creditReportService.fetchCreditReport("")));

        // Skicka till topic
//        .to(Const.ANO_LA_TOPIC,
//            Produced.with(Serdes.String(), JsonSerde.from(AnoLoanApplication.class)));


    /////////////////
    // Starta processen
    /////////////////
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "CreditReportFetcher");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    new KafkaStreamsBuilder(
        new StreamsConfig(props),
        builder,
        0,
        false,
        null);

    log.info("Started LoanApplicationSplitter DONE");
  }


}
