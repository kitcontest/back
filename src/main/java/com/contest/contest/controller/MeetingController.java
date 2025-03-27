package com.contest.contest.controller;

import com.contest.contest.entity.Meeting;
import com.contest.contest.service.MeetingApplicationService;  // 서비스 추가
import com.contest.contest.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/contests/{contestId}/meetings")
@CrossOrigin(origins = "http://localhost:127.0.0.1") // 리액트 개발 서버 주소
public class MeetingController {

  @Autowired
  private MeetingService meetingService;

  @Autowired
  private MeetingApplicationService meetingApplicationService;  // 서비스 주입

  /**
   * 특정 공모전에 대한 모임을 생성하는 API
   * POST /api/contests/{contestId}/meetings
   * 요청 본문에는 모임 생성자 ID, 모임 상세 설명, 모임 주제, 인원 제한, 공유 링크(선택)를 JSON 형식으로 전달
   */
  @PostMapping
  public ResponseEntity<Meeting> createMeeting(
      @PathVariable Long contestId,
      @RequestBody MeetingRequest meetingRequest) {

    Meeting meeting = meetingService.createMeeting(
        contestId,
        meetingRequest.getCreatorId(),
        meetingRequest.getDescription(),
        meetingRequest.getSubject(),
        meetingRequest.getCapacity(),
        meetingRequest.getShareLink()
    );
    return ResponseEntity.ok(meeting);
  }

  /**
   * GET /api/contests/{contestId}/meetings
   * 특정 공모전에 등록된 모임 목록을 반환하는 API
   */
  @GetMapping
  public ResponseEntity<List<Meeting>> getMeetingsByContest(@PathVariable Long contestId) {
    List<Meeting> meetings = meetingService.getMeetingsByContestId(contestId);
    return ResponseEntity.ok(meetings);
  }




  // Meeting 생성 요청을 위한 DTO
  public static class MeetingRequest {
    private Long creatorId;
    private String description;
    private String subject;
    private Integer capacity;
    private String shareLink;

    // Getters and Setters
    public Long getCreatorId() {
      return creatorId;
    }
    public void setCreatorId(Long creatorId) {
      this.creatorId = creatorId;
    }
    public String getDescription() {
      return description;
    }
    public void setDescription(String description) {
      this.description = description;
    }
    public String getSubject() {
      return subject;
    }
    public void setSubject(String subject) {
      this.subject = subject;
    }
    public Integer getCapacity() {
      return capacity;
    }
    public void setCapacity(Integer capacity) {
      this.capacity = capacity;
    }
    public String getShareLink() {
      return shareLink;
    }
    public void setShareLink(String shareLink) {
      this.shareLink = shareLink;
    }
  }

}
