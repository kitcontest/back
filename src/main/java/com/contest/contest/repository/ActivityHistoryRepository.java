package com.contest.contest.repository;

import com.contest.contest.entity.ActivityHistory;
import com.contest.contest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Long> {
  // 가장 최근 로그인 기록 중 logoutTime이 null인 레코드를 가져옴
  Optional<ActivityHistory> findTopByUserAndActivityTypeAndIpAddressAndLogoutTimeIsNullOrderByActivityDateDesc(
      User user, String activityType, String ipAddress);
}
