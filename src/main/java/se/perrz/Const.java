package se.perrz;

public class Const {
  public static final String LA_TOPIC = "loan-app";
  public static final String LA_RESP_TOPIC = "loan-app-response";
  public static final String REQ_CREDIT_REPORT_TOPIC = "event-credit-report-req";
  public static final String RESP_CREDIT_REPORT_TOPIC = "event-credit-report-resp";

  // <ApplGuid, AnoLoanApplication>
  public static final String ANO_LA_TOPIC = "ano-loan-app";

  // <PersonalId, Applicant>
  public static final String PERSON_LA_TOPIC = "person-loan-app";
  public static final long CREDIT_REPORT_LATENCY_MS = 1000;
}
