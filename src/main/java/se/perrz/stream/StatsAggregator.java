package se.perrz.stream;


import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.perrz.model.loan.LoanApplication;
import se.perrz.stream.serde.JsonSerde;
import se.perrz.stream.serde.KafkaStreamTypeValidator;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class StatsAggregator {

  private static final String TOPIC = "my-topic";
  private static final String SUMM_TOPIC = "sum-topic";
  Logger log = LoggerFactory.getLogger(StatsAggregator.class);

  private long counter = 0;

  private Serde<LoanApplication> laSerde = new JsonSerde<>(LoanApplication.class);

  /**
   * Sätter upp config för denna stream processor.
   */
  public StatsAggregator() {

    log.info("Starting StatsAggregator");
    /////////////////
    // Konfiguration av strömmen
    /////////////////
    StreamsBuilder builder = new StreamsBuilder();

//    KStream<String, LoanApplication> eventStream = setupValidatedStream(builder, TOPIC);

//    eventStream.foreach((key, value) -> {
//      if (log.isDebugEnabled()) {
//        log.debug("Incoming event: {}", value);
//      }
//      //Skriv en logg-rad var 10000:e event...
//      if (++counter % 10000 == 0) {
//        log.info("Processed 10 000 events since last time this was logged...");
//      }
//    });

    //Resulterar i en state-store som vi kan query'a på formen <personnummer, kim-id>
//    setupMapStream(eventStream, StreamProcessorUtil::getPersonalIdFromEvent,
//        topicPersonalIdUpdates);
//    setupGlobalKTable(builder, topicPersonalIdUpdates, stateStorePersonalId);
//
//    /////////////////
//    // Starta processen
//    /////////////////
//    new KafkaStreamsBuilder(
//        new StreamsConfig(kafkaSpringBootConfig.getIdMapStreamProps()),
//        builder,
//        restartMs,
//        cleanUpBeforeStart,
//        kafkaCustomerIdService::reloadViews);

    log.info("Started KimIdStreamProcessor DONE");
  }




  /**
   * Sätter upp en stream-process som filtrerar ut relevanta events, samt bygger upp en
   * ny ström utifrån valt ID (som key) och KIM-id (som value).
   */
  private void setupMapStream(KStream<String, LoanApplication> eventStream) {

    KTable<Windowed<String>, Long> windowedPageViewCounts = eventStream
        .groupByKey(Serialized.with(Serdes.String(), laSerde))
        .windowedBy(TimeWindows.of(TimeUnit.SECONDS.toMillis(5)))
        .count();


    // Skicka records, nu på formen <ssn/mmk-id, kim-id> till en ny topic
//    windowedPageViewCounts.toStream().to(SUMM_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
  }

  /**
   * Skapar upp en GlobalKTable utifrån en ström på formen [ssn/mmk-id, kim-id]. Denna backas
   * av en state-store med angivet namn.
   */
  private void setupGlobalKTable(KStreamBuilder builder, String inputTopic, String stateStoreName) {
    builder.globalTable(
        Serdes.String(), /* key serde */
        Serdes.Long(),   /* value serde */
        inputTopic,
        stateStoreName);
  }
}
