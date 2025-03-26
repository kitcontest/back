package com.contest.contest.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contests")
public class Contest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long contestId;

  @Column(length = 255)
  private String keywords;

  @Column(length = 255)
  private String title;

  @Column(length = 255)
  private String company;

  // due_date는 필요에 따라 DATE 타입으로 변경할 수 있으나 여기서는 간단히 String 사용
  @Column(length = 50)
  private String dueDate;

  private Integer viewCount;

  @Column(length = 255)
  private String thumbnailUrl;

  @Lob
  private String description;

  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  // Getters and Setters
  public Long getContestId() {
    return contestId;
  }
  public void setContestId(Long contestId) {
    this.contestId = contestId;
  }
  public String getKeywords() {
    return keywords;
  }
  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getCompany() {
    return company;
  }
  public void setCompany(String company) {
    this.company = company;
  }
  public String getDueDate() {
    return dueDate;
  }
  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }
  public Integer getViewCount() {
    return viewCount;
  }
  public void setViewCount(Integer viewCount) {
    this.viewCount = viewCount;
  }
  public String getThumbnailUrl() {
    return thumbnailUrl;
  }
  public void setThumbnailUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
