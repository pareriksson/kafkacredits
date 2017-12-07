package se.perrz.model.loan;

import java.util.Random;
import java.util.UUID;

public class Generator {

  private static final String[] FIRST_NAME_SET = {"Kalle", "Nils", "Johan", "Therese", "Linda"};
  private static final String[] LAST_NAME_SET = {"Johansson", "Vängman", "Didriksson", "Carlsson", "Svensson"};
  private static final String[] EMPLOYER_SET = {"Ericsson AB", "IBM", "Forefront Consulting", "Ica nära Riksten", "IKEA"};

  public static LoanApplication generate() {
    LoanApplication ret = new LoanApplication();
    ret.setId(UUID.randomUUID().toString());
    ret.setAmount(1000 * rnd(10, 350));
    ret.setAmortazionPeriod(rnd(12, 121));
    Applicant appl = new Applicant();
    ret.setApplicant(appl);

    appl.setFirstName(random(FIRST_NAME_SET));
    appl.setLastName(random(LAST_NAME_SET));
    appl.setEmployer(random(EMPLOYER_SET));
    appl.setSalary(1000 * rnd(15, 50));
    appl.setEmploymentType(EmploymentType.FULL_TIME);
    appl.setPersonId(generatePersonId());

    return ret;
  }

  private static Integer rnd(int lower, int upper) {
    if (lower > upper) {
      throw new IllegalArgumentException("Lower is larger than upper");
    }
    return new Random().nextInt(upper - lower) + lower;
  }

  private static String generatePersonId() {
    StringBuilder sb = new StringBuilder();
    sb.append(1950 + new Random().nextInt(50));
    sb.append(String.format("%02d", rnd(1, 13)));
    sb.append(String.format("%02d", rnd(1, 29)));
    sb.append(String.format("%04d", rnd(1, 9999)));

    return sb.toString();

  }

  private static String random(String[] set) {
    return set[new Random().nextInt(set.length)];
  }

}
