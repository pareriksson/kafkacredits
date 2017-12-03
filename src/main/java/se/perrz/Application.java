package se.perrz;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.perrz.dispatcher.Dispatcher;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

  Logger log = LoggerFactory.getLogger(Application.class);

  @Autowired Dispatcher dispatcher;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @PostConstruct
  public void run() {
    log.info("Emitting events");
    try {
      dispatcher.emit(20);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    log.info("Done");
  }


}

