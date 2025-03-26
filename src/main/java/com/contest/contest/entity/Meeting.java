package com.contest.contest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "meetings")
public class Meeting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long meetingId;

  // LAZY 로딩 시 직렬화 문제 방지를 위해 @JsonIgnoreProperties 사용
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contest_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Contest contest;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(length = 255)
  private String subject;

  private Integer capacity;



  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User creator;

  @Column(length = 20)
  private String approvalStatus = "pending";

  @Column(length = 255)
  private String shareLink;

  @Column(nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column
  private Integer currentParticipants = 1;

  // Getters and Setters
  public Long getMeetingId() {
    return meetingId;
  }
  public void setMeetingId(Long meetingId) {
    this.meetingId = meetingId;
  }
  public Contest getContest() {
    return contest;
  }
  public void setContest(Contest contest) {
    this.contest = contest;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public Integer getCapacity() {
    return capacity;
  }
  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }
  public User getCreator() {
    return creator;
  }
  public void setCreator(User creator) {
    this.creator = creator;
  }
  public String getApprovalStatus() {
    return approvalStatus;
  }
  public void setApprovalStatus(String approvalStatus) {
    this.approvalStatus = approvalStatus;
  }
  public String getShareLink() {
    return shareLink;
  }
  public void setShareLink(String shareLink) {
    this.shareLink = shareLink;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Integer getCurrentParticipants() {
    return currentParticipants;
  }
  public void setCurrentParticipants(Integer currentParticipants) {
    this.currentParticipants = currentParticipants;
  }

}
