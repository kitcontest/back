package com.contest.contest.repository;

import com.contest.contest.entity.Contest;
import com.contest.contest.entity.Meeting;
import java.util.List;
import com.contest.contest.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
  // 특정 공모전에 속한 모임 목록을 조회하는 메소드
  List<Meeting> findByContest(Contest contest);


  @Query("SELECT COUNT(mp) FROM MeetingParticipant mp WHERE mp.meeting.meetingId = :meetingId AND mp.status = 'approved'")
  Integer countApprovedParticipants(Long meetingId); // 승인된 참가자만 카운트

  // 특정 사용자가 생성자로 있는 모든 모임을 찾는 메소드
  List<Meeting> findByCreator(User creator);

}
