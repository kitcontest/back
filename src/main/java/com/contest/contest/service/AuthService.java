package com.contest.contest.service;

import com.contest.contest.entity.User;
import com.contest.contest.entity.ActivityHistory;
import com.contest.contest.repository.UserRepository;
import com.contest.contest.repository.ActivityHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ActivityHistoryRepository activityHistoryRepository;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public User register(String username, String password, String email) {
    if(userRepository.findByUsername(username).isPresent()){
      throw new RuntimeException("Username already exists");
    }
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);
    return userRepository.save(user);
  }

  public User login(String username, String password, String ipAddress) {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if(optionalUser.isEmpty()){
      throw new RuntimeException("Invalid username or password");
    }
    User user = optionalUser.get();
    if(!passwordEncoder.matches(password, user.getPassword())){
      throw new RuntimeException("Invalid username or password");
    }
    ActivityHistory history = new ActivityHistory();
    history.setUser(user);
    history.setActivityType("login");
    history.setIpAddress(ipAddress);
    history.setDetails("로그인 성공");
    history.setActivityDate(LocalDateTime.now());
    activityHistoryRepository.save(history);
    return user;
  }

  public void logout(Long userId, String ipAddress) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    Optional<ActivityHistory> optionalHistory =
        activityHistoryRepository.findTopByUserAndActivityTypeAndIpAddressAndLogoutTimeIsNullOrderByActivityDateDesc(
            user, "login", ipAddress);
    if(optionalHistory.isPresent()){
      ActivityHistory history = optionalHistory.get();
      history.setLogoutTime(LocalDateTime.now());
      history.setDetails(history.getDetails() + " / 로그아웃 완료");
      activityHistoryRepository.save(history);
    } else {
      throw new RuntimeException("No active login record found for logout");
    }
  }


  /**
   * 비밀번호 변경을 위한 메소드
   * @param userId 사용자 ID
   * @param oldPassword 기존 비밀번호
   * @param newPassword 새로운 비밀번호
   * @return 변경된 사용자
   */
  public User changePassword(Long userId, String oldPassword, String newPassword) {
    // 사용자 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // 기존 비밀번호와 입력한 비밀번호가 일치하는지 확인
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
      throw new RuntimeException("Incorrect old password");
    }

    // 새 비밀번호로 변경
    user.setPassword(passwordEncoder.encode(newPassword));

    // 사용자 비밀번호 업데이트
    return userRepository.save(user);
  }
}
