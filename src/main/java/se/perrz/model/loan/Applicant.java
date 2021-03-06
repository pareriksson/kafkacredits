package se.perrz.model.loan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

  public Integer getAge() {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    LocalDate dateOfBirth =
        LocalDate.parse(personId.substring(0, 8), dateTimeFormatter);

    return LocalDate.now().getYear() - dateOfBirth.getYear();

  }

  @Override public String toString() {
    final StringBuilder sb = new StringBuilder("Applicant{");
    sb.append("firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append(", personId='").append(personId).append('\'');
    sb.append(", employmentType=").append(employmentType);
    sb.append(", employer='").append(employer).append('\'');
    sb.append(", salary=").append(salary);
    sb.append('}');
    return sb.toString();
  }
}
