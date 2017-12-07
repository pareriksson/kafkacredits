package se.perrz.model.loan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Applicant {

  private String firstName;
  private String lastName;
  private String personId;

  private EmploymentType employmentType;
  private String employer;


  private Integer salary;


  public Integer getSalary() {
    return salary;
  }

  public Applicant setSalary(Integer salary) {
    this.salary = salary;
    return this;
  }

  public String getEmployer() {
    return employer;
  }

  public Applicant setEmployer(String employer) {
    this.employer = employer;
    return this;
  }

  public EmploymentType getEmploymentType() {
    return employmentType;
  }

  public Applicant setEmploymentType(EmploymentType employmentType) {
    this.employmentType = employmentType;
    return this;
  }


  public String getFirstName() {
    return firstName;
  }

  public Applicant setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public Applicant setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPersonId() {
    return personId;
  }

  public Applicant setPersonId(String personId) {
    this.personId = personId;
    return this;
  }
}
