package com.contest.contest.controller;


import com.contest.contest.entity.Contest;
import com.contest.contest.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

  @Autowired
  private ContestService contestService;

  /**
   * GET /api/contests
   * 데이터베이스에 저장된 모든 공모전 리스트를 JSON 형식으로 반환
   */
  @GetMapping
  public List<Contest> getContestList() {
    return contestService.getAllContests();
  }
  /**
   * GET /api/contests/{contestId}
   * 특정 공모전 ID에 해당하는 공모전 정보를 반환
   */
  @GetMapping("/{contestId}")
  public ResponseEntity<Contest> getContestById(@PathVariable Long contestId) {
    return contestService.getContestById(contestId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
