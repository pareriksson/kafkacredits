package se.perrz.service;

import org.junit.Assert;
import org.junit.Test;
import se.perrz.model.ext.CreditReport;

public class CreditReportServiceTest {

  @Test
  public void testIncome() {
    CreditReportService serv = new CreditReportService();
    CreditReport creditReport = serv.fetchCreditReport("201201011234");
    Assert.assertEquals(360000L, creditReport.getAnnualIncome().longValue());

  }

  @Test
  public void testPaymentRemark() {
    CreditReportService serv = new CreditReportService();

    CreditReport creditReport = serv.fetchCreditReport("201201011234");
    Assert.assertFalse(creditReport.getHasPaymentRemark());

    creditReport = serv.fetchCreditReport("201201011239");
    Assert.assertTrue(creditReport.getHasPaymentRemark());

  }

}
