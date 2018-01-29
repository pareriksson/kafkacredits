package se.perrz.stream;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.perrz.Const;
import se.perrz.model.event.CreditReportFetchedEvent;
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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class CreditReportFetcher {

  Logger log = LoggerFactory.getLogger(CreditReportFetcher.class);

  @Autowired CreditReportService creditReportService;



  /**
   * Sätter upp config för denna stream processor.
   */
  public CreditReportFetcher() {
    JsonSerde<Applicant> applicantSerde = JsonSerde.from(Applicant.class);
    log.info("Starting CreditReportFetcher");
    /////////////////
    // Konfiguration av strömmen
    /////////////////
    StreamsBuilder builder = new StreamsBuilder();
    KStream<String, RequestCreditReportEvent> creditEvent = KafkaStreamTypeValidator
        .validatedStream(builder, Const.REQ_CREDIT_REPORT_TOPIC, Serdes.String(), JsonSerde.from(RequestCreditReportEvent.class));

    //Behövs inte, redan på personkey
//    KafkaStreamTypeValidator
//        .validatedStream(builder, Const.PERSON_LA_TOPIC, Serdes.String(), applicantSerde)
//
//        // Nyckla om på personKey
//        .selectKey((key1, value1) -> PersonKey.from(value1.getPersonId()))
//
//        // Skicka ut på ny topic
//        .to(Const.PERSON_KEY_LA_TOPIC, Produced.with(Serdes.String(), applicantSerde));

    //Skapa lookup tabell
    KTable<String, Applicant> applicantsTable = builder.table(Const.PERSON_LA_TOPIC,
        Consumed.with(Serdes.String(), applicantSerde));

    //Key: PersonKey
//    KStream<String, CreditReport> reports = creditEvent.join(persons, (eventValue, person) -> {
//      return creditReportService.fetchCreditReport(person.getPersonId());
//    }, JoinWindows.of(TimeUnit.MINUTES.toMillis(1))
//    , Joined.with(Serdes.String(), JsonSerde.from(RequestCreditReportEvent.class), JsonSerde.from(Applicant.class)));

    //TODO här behövs kodas lite...

    KStream<String, Appclicant> applicantKStream = creditEvent.leftJoin(applicantsTable,
        // Joiner
        (reportEvent, applicant) -> {
          log.info("For {}, got {}", reportEvent.getPersonKey(), applicant);
          return applicant;
        }, Joined
            .with(Serdes.String(), JsonSerde.from(RequestCreditReportEvent.class), applicantSerde));


//    reports
//        // Bygg om till ny ström för låneflödet
//        .mapValues((value) -> {
//          CreditReportFetchedEvent event = new CreditReportFetchedEvent();
//          event.setId(UUID.randomUUID().toString());
//          event.setPersonKey(value.getPersonKey());
//          event.setCreditReport(value);
//          event.setForLoanAppl(null);
//          return event;
//        })
//        .to(Const.RESP_CREDIT_REPORT_TOPIC, Produced.with(Serdes.String(), JsonSerde.from(CreditReportFetchedEvent.class) ));


    /////////////////
    // Starta processen
    /////////////////
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "CreditReportFetcher");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.from(Applicant.class).getClass());

    new KafkaStreamsBuilder(
        new StreamsConfig(props),
        builder,
        0,
        false,
        null);

    log.info("Started CreditReportFetcher DONE");
  }


}
