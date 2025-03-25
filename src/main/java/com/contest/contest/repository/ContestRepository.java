package com.contest.contest.repository;


import com.contest.contest.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
  // 추가 쿼리 메소드가 필요하면 이곳에 정의 (예: 키워드 검색 등)
}
