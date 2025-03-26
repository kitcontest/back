package com.contest.contest.repository;

import com.contest.contest.entity.MeetingParticipant;
import com.contest.contest.entity.MeetingParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, MeetingParticipantId> {

  // 특정 모임에 해당 사용자가 이미 신청했는지 확인하는 메소드
  Optional<MeetingParticipant> findByMeeting_MeetingIdAndUser_UserId(Long meetingId, Long userId);

  // 참가자 상태를 'approved'로 변경
  @Modifying
  @Query("UPDATE MeetingParticipant mp SET mp.status = 'approved' WHERE mp.meeting.meetingId = :meetingId AND mp.user.userId = :userId")
  void approveParticipant(Long meetingId, Long userId);

  // 참가자를 목록에서 제거 (거부)
  @Modifying
  @Query("DELETE FROM MeetingParticipant mp WHERE mp.meeting.meetingId = :meetingId AND mp.user.userId = :userId")
  void rejectParticipant(Long meetingId, Long userId);

  // 특정 모임에 대해 'approved' 상태인 참가자 수를 구하는 메소드
  @Query("SELECT COUNT(mp) FROM MeetingParticipant mp WHERE mp.meeting.meetingId = :meetingId AND mp.status = 'approved'")
  Integer countByMeeting_MeetingIdAndStatus(Long meetingId, String status);

  // 신청자가 거부 상태일 때, 그 신청자를 일괄적으로 삭제하는 메소드
  @Modifying
  @Query("DELETE FROM MeetingParticipant mp WHERE mp.meeting.meetingId = :meetingId AND mp.status = 'rejected'")
  void deleteRejectedParticipants(Long meetingId);

}
