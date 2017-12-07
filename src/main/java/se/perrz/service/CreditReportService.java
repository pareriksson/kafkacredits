package se.perrz.service;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import se.perrz.Const;
import se.perrz.model.event.RequestCreditReportEvent;
import se.perrz.model.ext.CreditReport;
import se.perrz.model.util.PersonKey;

@Service
public class CreditReportService {

  public CreditReport fetchCreditReport(String personalId) {
    Preconditions.checkNotNull(personalId);
    Preconditions.checkArgument(personalId.length() == 12, "invalid len " + personalId.length());

    //Simulate WS call
    try {
      Thread.currentThread().sleep(Const.CREDIT_REPORT_LATENCY_MS);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    CreditReport ret = new CreditReport();
    ret.setPersonKey(PersonKey.from(personalId));
    ret.setAnnualIncome(Integer.parseInt(personalId.substring(10, 11)) * 10000 * 12);
    ret.setHasPaymentRemark(personalId.substring(11, 12).equals("9"));
    return ret;

  }
}
