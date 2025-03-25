package com.contest.contest.repository;

import com.contest.contest.entity.Contest;
import com.contest.contest.entity.Meeting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
  // 특정 공모전에 속한 모임 목록을 조회하는 메소드
  List<Meeting> findByContest(Contest contest);}
