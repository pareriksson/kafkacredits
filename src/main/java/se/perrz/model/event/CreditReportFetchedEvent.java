package se.perrz.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.perrz.model.ext.CreditReport;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditReportFetchedEvent {
  private String id;

  private String personKey;

  private String forLoanAppl;

  private CreditReport creditReport;

  public CreditReport getCreditReport() {
    return creditReport;
  }

  public CreditReportFetchedEvent setCreditReport(CreditReport creditReport) {
    this.creditReport = creditReport;
    return this;
  }

  public String getId() {
    return id;
  }

  public CreditReportFetchedEvent setId(String id) {
    this.id = id;
    return this;
  }

  public String getPersonKey() {
    return personKey;
  }

  public CreditReportFetchedEvent setPersonKey(String personKey) {
    this.personKey = personKey;
    return this;
  }

  public String getForLoanAppl() {
    return forLoanAppl;
  }

  public CreditReportFetchedEvent setForLoanAppl(String forLoanAppl) {
    this.forLoanAppl = forLoanAppl;
    return this;
  }
}
