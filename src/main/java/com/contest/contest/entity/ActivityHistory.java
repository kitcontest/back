package com.contest.contest.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_history")
public class ActivityHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long historyId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, length = 50)
  private String activityType; // 예: "login", "contest_activity" 등

  private Integer referenceId; // 공모전 또는 모임 등 관련 참조

  @Column(nullable = false)
  private LocalDateTime activityDate = LocalDateTime.now();

  // 로그아웃 시 업데이트됨
  private LocalDateTime logoutTime;

  @Column(columnDefinition = "TEXT")
  private String details;

  @Column(length = 45)
  private String ipAddress;

  // Getters and Setters
  public Long getHistoryId() {
    return historyId;
  }
  public void setHistoryId(Long historyId) {
    this.historyId = historyId;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public String getActivityType() {
    return activityType;
  }
  public void setActivityType(String activityType) {
    this.activityType = activityType;
  }
  public Integer getReferenceId() {
    return referenceId;
  }
  public void setReferenceId(Integer referenceId) {
    this.referenceId = referenceId;
  }
  public LocalDateTime getActivityDate() {
    return activityDate;
  }
  public void setActivityDate(LocalDateTime activityDate) {
    this.activityDate = activityDate;
  }
  public LocalDateTime getLogoutTime() {
    return logoutTime;
  }
  public void setLogoutTime(LocalDateTime logoutTime) {
    this.logoutTime = logoutTime;
  }
  public String getDetails() {
    return details;
  }
  public void setDetails(String details) {
    this.details = details;
  }
  public String getIpAddress() {
    return ipAddress;
  }
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
}
