package com.contest.contest.controller;


import com.contest.contest.entity.User;
import com.contest.contest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
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
}
