package se.perrz.dispatcher;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import se.perrz.Const;
import se.perrz.model.loan.Generator;
import se.perrz.model.loan.LoanApplication;

import java.util.Properties;
import java.util.UUID;

@Component
public class Dispatcher {


  private ObjectMapper mapper = new ObjectMapper();

  public void emit(int num) throws JsonProcessingException {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer<>(props);
    for (int i = 0; i < num; i++) {
      LoanApplication generated = Generator.generate();
      String val = mapper.writeValueAsString(generated);
      producer.send(new ProducerRecord<String, String>(Const.LA_TOPIC, UUID.randomUUID().toString(), val));
    }


    producer.close();
  }
}
