package se.perrz.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.perrz.model.ext.CreditReport;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationRejectedEvent {
  private String id;

  public static ApplicationRejectedEvent forId(String id) {
    ApplicationRejectedEvent ret = new ApplicationRejectedEvent();
    ret.setId(id);
    return ret;
  }

  public String getId() {
    return id;
  }

  public ApplicationRejectedEvent setId(String id) {
    this.id = id;
    return this;
  }
}
