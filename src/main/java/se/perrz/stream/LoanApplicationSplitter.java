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
import org.springframework.stereotype.Component;
import se.perrz.Const;
import se.perrz.model.internal.AnoLoanApplication;
import se.perrz.model.loan.Applicant;
import se.perrz.model.loan.LoanApplication;
import se.perrz.model.util.PersonKey;
import se.perrz.stream.serde.JsonSerde;
import se.perrz.stream.serde.KafkaStreamTypeValidator;

import java.util.Properties;

@Component
public class LoanApplicationSplitter {

  Logger log = LoggerFactory.getLogger(LoanApplicationSplitter.class);

  /**
   * Sätter upp config för denna stream processor.
   */
  public LoanApplicationSplitter() {

    log.info("Starting LoanApplicationSplitter");
    /////////////////
    // Konfiguration av strömmen
    /////////////////
    StreamsBuilder builder = new StreamsBuilder();

    KStream<String, LoanApplication> applicationKStream = KafkaStreamTypeValidator
        .validatedStream(builder, Const.LA_TOPIC, Serdes.String(), JsonSerde.from(LoanApplication.class));

    applicationKStream
        // Bygg om till ny ström för låneflödet
        .map((key, value) -> KeyValue.pair(key,
            AnoLoanApplication.from(value)))

        // Skicka till topic
        .to(Const.ANO_LA_TOPIC, Produced.with(Serdes.String(), JsonSerde.from(AnoLoanApplication.class)));

    applicationKStream
        // Bygg om till ny ström för kundflödet
        .map((key, value) -> KeyValue.pair(PersonKey.from(value.getApplicant().getPersonId()),
            value.getApplicant()))

        // Skicka till topic
        .to(Const.PERSON_LA_TOPIC, Produced.with(Serdes.String(), JsonSerde.from(Applicant.class)));


    /////////////////
    // Starta processen
    /////////////////
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "splitter");
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
