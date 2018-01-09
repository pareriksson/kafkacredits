package se.perrz.model.loan;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicantTest {

  @Test
  public void testAge() {
    Applicant applicant = new Applicant();
    applicant.setPersonId("201511019999");
    Assert.assertTrue(applicant.getAge() > 1);

  }

}
