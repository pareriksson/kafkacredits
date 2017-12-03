package se.perrz.model.loan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanApplication {

  private Applicant applicant;

  private Integer amount;

  private Integer amortazionPeriod;

  public Applicant getApplicant() {
    return applicant;
  }

  public LoanApplication setApplicant(Applicant applicant) {
    this.applicant = applicant;
    return this;
  }

  public Integer getAmount() {
    return amount;
  }

  public LoanApplication setAmount(Integer amount) {
    this.amount = amount;
    return this;
  }

  public Integer getAmortazionPeriod() {
    return amortazionPeriod;
  }

  public LoanApplication setAmortazionPeriod(Integer amortazionPeriod) {
    this.amortazionPeriod = amortazionPeriod;
    return this;
  }
}
