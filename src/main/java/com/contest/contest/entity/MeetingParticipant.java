package com.contest.contest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_participants")
public class MeetingParticipant {

  @EmbeddedId
  private MeetingParticipantId id = new MeetingParticipantId();

  // 모임 (LAZY 로딩 시 직렬화 문제 방지를 위해 @JsonIgnoreProperties 추가)
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("meetingId")
  @JoinColumn(name = "meeting_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Meeting meeting;

  // 사용자 (신청자)
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;

  @Column(length = 20)
  private String status = "pending";  // 신청대기 상태

  @Column(nullable = false)
  private LocalDateTime joinedAt = LocalDateTime.now();

  public MeetingParticipant() {}

  public MeetingParticipant(Meeting meeting, User user) {
    this.meeting = meeting;
    this.user = user;
    this.id = new MeetingParticipantId(meeting.getMeetingId(), user.getUserId());
  }

  public MeetingParticipantId getId() {
    return id;
  }
  public void setId(MeetingParticipantId id) {
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
