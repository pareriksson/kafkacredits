package se.perrz.model.ext;

public class CreditReport {

  private String personKey;

  private Integer annualIncome;

  private Boolean hasPaymentRemark;

  public String getPersonKey() {
    return personKey;
  }

  public CreditReport setPersonKey(String personKey) {
    this.personKey = personKey;
    return this;
  }

  public Integer getAnnualIncome() {
    return annualIncome;
  }

  public CreditReport setAnnualIncome(Integer annualIncome) {
    this.annualIncome = annualIncome;
    return this;
  }

  public Boolean getHasPaymentRemark() {
    return hasPaymentRemark;
  }

  public CreditReport setHasPaymentRemark(Boolean hasPaymentRemark) {
    this.hasPaymentRemark = hasPaymentRemark;
    return this;
  }
}
