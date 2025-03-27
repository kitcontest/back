package com.contest.contest.controller;

import com.contest.contest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * 회원 탈퇴 API
   * @param userId 탈퇴할 사용자의 ID
   * @return 삭제 완료 메시지
   */
  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok("User successfully deleted");
  }

}
