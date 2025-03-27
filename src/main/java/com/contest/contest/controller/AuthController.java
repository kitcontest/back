package com.contest.contest.controller;


import com.contest.contest.entity.User;
import com.contest.contest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:127.0.0.1") // 리액트 개발 서버 주소
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/register")
  public User register(@RequestParam String username,
      @RequestParam String password,
      @RequestParam String email) {
    return authService.register(username, password, email);
  }

  @PostMapping("/login")
  public User login(@RequestParam String username,
      @RequestParam String password,
      HttpServletRequest request) {
    String ipAddress = request.getRemoteAddr();
    return authService.login(username, password, ipAddress);
  }

  @PostMapping("/logout")
  public String logout(@RequestParam Long userId,
      HttpServletRequest request) {
    String ipAddress = request.getRemoteAddr();
    authService.logout(userId, ipAddress);
    return "Logout successful";
  }

  /**
   * 비밀번호 변경 API
   * @param userId 사용자 ID
   * @param oldPassword 기존 비밀번호
   * @param newPassword 새 비밀번호
   * @return 변경된 사용자 정보
   */
  @PatchMapping("/change-password/{userId}")
  public String changePassword(@PathVariable Long userId,
      @RequestParam String oldPassword,
      @RequestParam String newPassword) {
    try {
      authService.changePassword(userId, oldPassword, newPassword);
      return "Password changed successfully";
    } catch (RuntimeException e) {
      return e.getMessage();
    }
  }
}
