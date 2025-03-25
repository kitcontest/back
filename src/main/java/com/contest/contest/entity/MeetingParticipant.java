package com.contest.contest.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MeetingParticipants")
public class MeetingParticipant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;  // 신청 내역의 고유 ID

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "meeting_id", nullable = false)
  private Meeting meeting;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  // 신청 상태: "pending" (기본), "approved", "rejected"
  @Column(length = 20, nullable = false)
  private String status = "pending";

  @Column(nullable = false)
  private LocalDateTime joinedAt = LocalDateTime.now();

  // Getters and Setters

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Meeting getMeeting() {
    return meeting;
  }
  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }
  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public LocalDateTime getJoinedAt() {
    return joinedAt;
  }
  public void setJoinedAt(LocalDateTime joinedAt) {
    this.joinedAt = joinedAt;
  }
}
