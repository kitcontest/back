package com.contest.contest.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MeetingParticipantId implements Serializable {

  private Long meetingId;
  private Long userId;

  public MeetingParticipantId() { }

  public MeetingParticipantId(Long meetingId, Long userId) {
    this.meetingId = meetingId;
    this.userId = userId;
  }

  public Long getMeetingId() {
    return meetingId;
  }
  public void setMeetingId(Long meetingId) {
    this.meetingId = meetingId;
  }
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MeetingParticipantId)) return false;
    MeetingParticipantId that = (MeetingParticipantId) o;
    return Objects.equals(getMeetingId(), that.getMeetingId()) &&
        Objects.equals(getUserId(), that.getUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMeetingId(), getUserId());
  }
}
