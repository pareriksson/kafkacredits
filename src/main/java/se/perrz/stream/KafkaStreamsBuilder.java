package se.perrz.stream;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.internals.StreamThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Komponent som givet en konfiguration startar en KafkaStreams samt hanterar omstart av den.
 */
public class KafkaStreamsBuilder {

  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStreamsBuilder.class);

  public KafkaStreamsBuilder(StreamsConfig streamsConfig,
      StreamsBuilder transactionStreamBuilder,
      long streamRestartSleepMs,
      boolean cleanUpBeforeStart,
      Consumer<KafkaStreams> onStartedCallback) {

    KafkaStreams kafkaStreams = new KafkaStreams(transactionStreamBuilder.build(), streamsConfig);

    //Detta vill man endast göra "ibland" (sällan i produktion). Resulterar i att
    //state-store's rensas och laddas om.
    if (cleanUpBeforeStart) {
      LOGGER.info("Cleaning up state store's before starting...");
      kafkaStreams.cleanUp();
      LOGGER.info("Done cleaning up.");
    }

    kafkaStreams.setUncaughtExceptionHandler((Thread currentThread, Throwable throwable) -> {
      LOGGER.error("-- | Uncaught exception in stream.", throwable);
    });

    Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    LOGGER.info("Trying to start the stream...");
    kafkaStreams.start();
    LOGGER.info("Stream started.");
    if (onStartedCallback != null) {
      onStartedCallback.accept(kafkaStreams);
    }

  }
}
