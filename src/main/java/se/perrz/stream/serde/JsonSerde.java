package se.perrz.stream.serde;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class JsonSerde<T> implements Serde<T> {
  private final JsonSerializer<T> serializer;
  private final JsonDeserializer<T> deserializer;


  /**
   * Generic Serde for serilize and deserialize JSON data.
   *
   * @param clazz root model clazz type.
   */
  public JsonSerde(Class<T> clazz) {
    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.registerModule(new JodaModule());
    objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    this.serializer = new JsonSerializer<>(objectMapper);
    this.deserializer = new JsonDeserializer<>(clazz, objectMapper);
  }

  public static <T> JsonSerde<T> from(Class<T> clazz) {
    return new JsonSerde<>(clazz);
  }

  public void configure(Map<String, ?> configs, boolean isKey) {
    this.serializer.configure(configs, isKey);
    this.deserializer.configure(configs, isKey);
  }

  public void close() {
    this.serializer.close();
    this.deserializer.close();
  }

  public Serializer<T> serializer() {
    return this.serializer;
  }

  public Deserializer<T> deserializer() {
    return this.deserializer;
  }
}
