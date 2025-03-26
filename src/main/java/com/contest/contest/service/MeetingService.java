package com.contest.contest.service;
import com.contest.contest.entity.Meeting;
import com.contest.contest.entity.MeetingParticipant;
import com.contest.contest.repository.MeetingRepository;
import com.contest.contest.repository.MeetingParticipantRepository;
import com.contest.contest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.contest.contest.entity.Contest;
import com.contest.contest.entity.Meeting;
import com.contest.contest.entity.MeetingParticipant;
import com.contest.contest.entity.User;
import com.contest.contest.repository.ContestRepository;
import com.contest.contest.repository.MeetingRepository;
import com.contest.contest.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingService {

  @Autowired
  private MeetingRepository meetingRepository;

  @Autowired
  private ContestRepository contestRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MeetingParticipantRepository meetingParticipantRepository;
  /**
   * 특정 공모전의 모임을 생성합니다.
   * @param contestId 공모전 ID
   * @param creatorId 모임 생성자 ID
   * @param description 모임 상세 설명
   * @param subject 모임 주제
   * @param capacity 모임 인원 제한
   * @param shareLink 공유 링크 (선택사항)
   * @return 생성된 Meeting 객체
   */
  public Meeting createMeeting(Long contestId, Long creatorId, String description, String subject, Integer capacity, String shareLink) {
    Contest contest = contestRepository.findById(contestId)
        .orElseThrow(() -> new RuntimeException("Contest not found"));

    User creator = userRepository.findById(creatorId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Meeting meeting = new Meeting();
    meeting.setContest(contest);
    meeting.setCreator(creator);
    meeting.setDescription(description);
    meeting.setSubject(subject);
    meeting.setCapacity(capacity);
    meeting.setShareLink(shareLink);
    // approvalStatus 기본값 "pending" 및 createdAt은 기본 생성 시 자동 설정됨

    return meetingRepository.save(meeting);
  }

  /**
   * 특정 공모전에 속한 모임 목록을 반환합니다.
   *
   * @param contestId 공모전 ID
   * @return 해당 공모전에 등록된 모든 모임 목록
   */
  public List<Meeting> getMeetingsByContestId(Long contestId) {
    // 공모전 조회
    Contest contest = contestRepository.findById(contestId)
        .orElseThrow(() -> new RuntimeException("Contest not found"));

    // 공모전에 속한 모임들 조회
    List<Meeting> meetings = meetingRepository.findByContest(contest);

    // 각 모임에 대해 현재 인원수를 업데이트
    for (Meeting meeting : meetings) {
      // 승인된 참가자 수 계산
      Integer approvedCount = meetingRepository.countApprovedParticipants(meeting.getMeetingId());
      // 생성자 포함하여 계산
      meeting.setCurrentParticipants(approvedCount + 1);  // 생성자 포함
    }

    return meetings;
  }


}
