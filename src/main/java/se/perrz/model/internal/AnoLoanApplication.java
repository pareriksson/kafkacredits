package se.perrz.model.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.BeanUtils;
import se.perrz.model.loan.Applicant;
import se.perrz.model.loan.LoanApplication;
import se.perrz.model.util.PersonKey;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnoLoanApplication {
  private String id;

  private String personKey;

  private Integer amount;

  private Integer amortazionPeriod;

  public String getId() {
    return id;
  }

  public AnoLoanApplication setId(String id) {
    this.id = id;
    return this;
  }

  public String getPersonKey() {
    return personKey;
  }

  public AnoLoanApplication setPersonKey(String personKey) {
    this.personKey = personKey;
    return this;
  }

  public Integer getAmount() {
    return amount;
  }

  public AnoLoanApplication setAmount(Integer amount) {
    this.amount = amount;
    return this;
  }

  public Integer getAmortazionPeriod() {
    return amortazionPeriod;
  }

  public AnoLoanApplication setAmortazionPeriod(Integer amortazionPeriod) {
    this.amortazionPeriod = amortazionPeriod;
    return this;
  }

  public static AnoLoanApplication from(LoanApplication value) {
    AnoLoanApplication ret = new AnoLoanApplication();
    BeanUtils.copyProperties(value, ret);
    ret.setPersonKey(PersonKey.from(value.getApplicant().getPersonId()));
    return ret;
  }
}
