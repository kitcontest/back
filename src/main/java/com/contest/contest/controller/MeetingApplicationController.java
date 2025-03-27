package com.contest.contest.controller;

import com.contest.contest.entity.MeetingParticipant;
import com.contest.contest.service.MeetingApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings/{meetingId}/apply")
@CrossOrigin(origins = "http://localhost:127.0.0.1") // 리액트 개발 서버 주소
public class MeetingApplicationController {

  @Autowired
  private MeetingApplicationService meetingApplicationService;

  /**
   * POST /api/meetings/{meetingId}/apply
   * 사용자가 특정 모임에 신청하면, 신청대기 상태로 저장합니다.
   * 요청 본문에는 신청자(userId)를 JSON 형식으로 전달합니다.
   */
  @PostMapping
  public ResponseEntity<MeetingParticipant> applyToMeeting(
      @PathVariable Long meetingId,
      @RequestBody ApplyRequest applyRequest) {

    MeetingParticipant meetingParticipant = meetingApplicationService.applyToMeeting(meetingId, applyRequest.getUserId());
    return ResponseEntity.ok(meetingParticipant);
  }

  /**
   * PATCH /api/meetings/{meetingId}/participants/{userId}/approve
   * 사용자가 특정 모임에 대해 신청을 승인하는 API
   */
  @PatchMapping("/participants/{userId}/approve")
  public ResponseEntity<String> approveParticipant(
      @PathVariable Long meetingId,
      @PathVariable Long userId) {
    meetingApplicationService.approveParticipant(meetingId, userId);  // 승인 로직 호출
    return ResponseEntity.ok("Participant approved successfully");
  }

  /**
   * PATCH /api/meetings/{meetingId}/participants/{userId}/reject
   * 사용자가 특정 모임에 대해 신청을 거부하는 API
   */
  @PatchMapping("/participants/{userId}/reject")
  public ResponseEntity<String> rejectParticipant(
      @PathVariable Long meetingId,
      @PathVariable Long userId) {
    meetingApplicationService.rejectParticipant(meetingId, userId);  // 거부 로직 호출
    return ResponseEntity.ok("Participant rejected successfully");
  }

  // 신청 요청을 위한 DTO
  public static class ApplyRequest {
    private Long userId;

    public Long getUserId() {
      return userId;
    }
    public void setUserId(Long userId) {
      this.userId = userId;
    }
  }
}
