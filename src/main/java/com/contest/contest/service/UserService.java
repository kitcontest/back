package com.contest.contest.service;

import com.contest.contest.entity.Meeting;
import com.contest.contest.entity.User;
import com.contest.contest.repository.MeetingRepository;
import com.contest.contest.repository.MeetingParticipantRepository;
import com.contest.contest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contest.contest.entity.User;
import com.contest.contest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MeetingRepository meetingRepository;

  @Autowired
  private MeetingParticipantRepository meetingParticipantRepository;


  /**
   * 회원 탈퇴 처리
   * @param userId 탈퇴할 사용자의 ID
   */
  @Transactional
  public void deleteUser(Long userId) {
    // 사용자 정보 확인
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // 사용자가 참여자로 있는 모든 모임 처리
    meetingParticipantRepository.findByUser_UserId(userId).forEach(meetingParticipant -> {
      Meeting meeting = meetingParticipant.getMeeting();
      // 모임에서 사용자 삭제
      meetingParticipantRepository.delete(meetingParticipant);

      // currentParticipants 값 감소
      meeting.setCurrentParticipants(meeting.getCurrentParticipants() - 1);

      // 모임 상태를 업데이트 (참여자 수가 capacity 미만이면 상태를 'pending'으로 변경)
      if (meeting.getCurrentParticipants() < meeting.getCapacity()) {
        meeting.setApprovalStatus("pending");
      } else if (meeting.getCurrentParticipants() == meeting.getCapacity()) {
        meeting.setApprovalStatus("full");
      }

      // 모임 저장
      meetingRepository.save(meeting);
    });

    // 사용자가 생성자로 있는 모임 삭제
    meetingRepository.findByCreator(user).forEach(meeting -> {
      meetingRepository.delete(meeting);
    });

    // 사용자 삭제
    userRepository.delete(user);
  }




}
