package com.contest.contest.service;

import com.contest.contest.entity.Meeting;
import com.contest.contest.entity.MeetingParticipant;
import com.contest.contest.entity.User;
import com.contest.contest.repository.MeetingParticipantRepository;
import com.contest.contest.repository.MeetingRepository;
import com.contest.contest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingApplicationService {

  @Autowired
  private MeetingRepository meetingRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MeetingParticipantRepository meetingParticipantRepository;


  /**
   * 특정 모임에 대해 사용자가 신청(pending)하는 로직
   * @param meetingId 모임 ID
   * @param userId 신청자 ID
   * @return 생성된 MeetingParticipant 객체
   */
  public MeetingParticipant applyToMeeting(Long meetingId, Long userId) {
    // 모임 조회
    Meeting meeting = meetingRepository.findById(meetingId)
        .orElseThrow(() -> new RuntimeException("Meeting not found"));

    // 모임이 이미 'full' 상태인 경우
    if (meeting.getApprovalStatus().equals("full")) {
      throw new RuntimeException("Meeting is full");
    }

    // 사용자 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // 중복 신청 확인
    meetingParticipantRepository.findByMeeting_MeetingIdAndUser_UserId(meetingId, userId)
        .ifPresent(mp -> { throw new RuntimeException("Already applied to meeting"); });

    // 신청 생성 (상태는 기본 "pending")
    MeetingParticipant meetingParticipant = new MeetingParticipant(meeting, user);

    // 신청 대기 상태로 저장
    meetingParticipantRepository.save(meetingParticipant);

    return meetingParticipant;
  }
  @Transactional
  public void approveParticipant(Long meetingId, Long userId) {
    // 모임과 사용자가 존재하는지 확인
    Meeting meeting = meetingRepository.findById(meetingId)
        .orElseThrow(() -> new RuntimeException("Meeting not found"));

    // 신청자가 존재하는지 확인
    MeetingParticipant meetingParticipant = meetingParticipantRepository
        .findByMeeting_MeetingIdAndUser_UserId(meetingId, userId)
        .orElseThrow(() -> new RuntimeException("Participant not found"));

    // 모임이 이미 'full' 상태인 경우, 더 이상 신청을 승인할 수 없음
    if (meeting.getApprovalStatus().equals("full")) {
      // 신청자는 거부되어야 함
      meetingParticipant.setStatus("rejected"); // 거절 상태로 설정
      meetingParticipantRepository.save(meetingParticipant);

      // 제한인원 초과로 승인 불가 안내
      throw new RuntimeException("Meeting is full, cannot approve participant.");  // 에러 메시지
    }

    // 신청 상태를 'approved'로 변경
    meetingParticipant.setStatus("approved");
    meetingParticipantRepository.save(meetingParticipant);

    // 현재 인원수 업데이트 (승인된 참여자 수 + 생성자 포함)
    Integer approvedCount = meetingParticipantRepository.countByMeeting_MeetingIdAndStatus(meetingId, "approved");
    meeting.setCurrentParticipants(approvedCount + 1);  // 생성자 포함된 인원 수

    // 모임이 가득 찼다면 상태를 'full'로 변경
    if (meeting.getCurrentParticipants() >= meeting.getCapacity()) {
      meeting.setApprovalStatus("full"); // 상태를 'full'로 변경
    }

    // 모임 저장
    meetingRepository.save(meeting);
  }




  /**
   * 특정 참가자를 거부 처리하는 메소드
   */
  @Transactional
  public void rejectParticipant(Long meetingId, Long userId) {
    // 모임과 사용자가 존재하는지 확인
    Meeting meeting = meetingRepository.findById(meetingId)
        .orElseThrow(() -> new RuntimeException("Meeting not found"));

    // 신청자가 존재하는지 확인
    meetingParticipantRepository.findByMeeting_MeetingIdAndUser_UserId(meetingId, userId)
        .orElseThrow(() -> new RuntimeException("Participant not found"));

    // 신청을 거부하여 목록에서 제거
    meetingParticipantRepository.rejectParticipant(meetingId, userId);

    // 현재 인원수 업데이트 (생성자 포함)
    Integer approvedCount = meetingRepository.countApprovedParticipants(meetingId);
    meeting.setCurrentParticipants(approvedCount);  // 생성자 포함된 인원 수

    // 현재인원 수를 감소시켜야 할 경우
    meeting.setCurrentParticipants(meeting.getCurrentParticipants() - 1);  // 인원 수 감소

    // 모임 상태를 다시 확인하여 'full' 상태가 아니면 'pending'으로 변경
    if (meeting.getCurrentParticipants() < meeting.getCapacity()) {
      meeting.setApprovalStatus("pending"); // 상태를 'pending'으로 변경
    }

    // 모임 저장
    meetingRepository.save(meeting);
  }
}
