package com.techlooper.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

/**
 * Created by phuonghqh on 5/5/15.
 */
@Document(indexName = "techlooper", type = "salaryReview")
public class SalaryReview {

  @Id
  private Long id;

  @Field
  private String jobTitle;

  @Field
  private List<Integer> jobLevelIds;

  @Field
  private Long locationId;

  @Field
  private Integer netSalary;

  @Field
  private List<String> skills;

  @Field
  private String reportTo;

  @Field
  private List<Long> jobCategories;

  @Field
  private Long companySizeId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public List<Integer> getJobLevelIds() {
    return jobLevelIds;
  }

  public void setJobLevelIds(List<Integer> jobLevelIds) {
    this.jobLevelIds = jobLevelIds;
  }

  public Long getLocationId() {
    return locationId;
  }

  public void setLocationId(Long locationId) {
    this.locationId = locationId;
  }

  public Integer getNetSalary() {
    return netSalary;
  }

  public void setNetSalary(Integer netSalary) {
    this.netSalary = netSalary;
  }

  public List<String> getSkills() {
    return skills;
  }

  public void setSkills(List<String> skills) {
    this.skills = skills;
  }

  public String getReportTo() {
    return reportTo;
  }

  public void setReportTo(String reportTo) {
    this.reportTo = reportTo;
  }

  public List<Long> getJobCategories() {
    return jobCategories;
  }

  public void setJobCategories(List<Long> jobCategories) {
    this.jobCategories = jobCategories;
  }

  public Long getCompanySizeId() {
    return companySizeId;
  }

  public void setCompanySizeId(Long companySizeId) {
    this.companySizeId = companySizeId;
  }
}
