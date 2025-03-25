package com.contest.contest.service;
import com.contest.contest.entity.Contest;
import com.contest.contest.repository.ContestRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContestService {

  @Autowired
  private ContestRepository contestRepository;

  /**
   * 데이터베이스에 저장된 모든 공모전 리스트를 반환
   */
  public List<Contest> getAllContests() {
    return contestRepository.findAll();
  }
  /**
   * 공모전 ID에 해당하는 공모전 정보를 반환
   */
  public Optional<Contest> getContestById(Long contestId) {
    return contestRepository.findById(contestId);
  }
}
