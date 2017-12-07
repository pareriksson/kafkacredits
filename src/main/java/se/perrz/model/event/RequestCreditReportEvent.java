package se.perrz.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.BeanUtils;
import se.perrz.model.loan.LoanApplication;
import se.perrz.model.util.PersonKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCreditReportEvent {
  private String id;

  private String personKey;

  private String forLoanAppl;


  public String getId() {
    return id;
  }

  public RequestCreditReportEvent setId(String id) {
    this.id = id;
    return this;
  }

  public String getPersonKey() {
    return personKey;
  }

  public RequestCreditReportEvent setPersonKey(String personKey) {
    this.personKey = personKey;
    return this;
  }

  public String getForLoanAppl() {
    return forLoanAppl;
  }

  public RequestCreditReportEvent setForLoanAppl(String forLoanAppl) {
    this.forLoanAppl = forLoanAppl;
    return this;
  }
}
