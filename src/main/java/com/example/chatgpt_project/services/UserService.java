package com.example.chatgpt_project.services;

import com.example.chatgpt_project.model.User;
import com.example.chatgpt_project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Create a new user
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // bcrypt encode password
        return userRepository.save(user);
    }

    // Edit user (reset password)
    public User editUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword)); // bcrypt encode new password
        return userRepository.save(user);
    }

    // Edit user (toggle unlocked status)
    public User toggleUserUnlock(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUnlocked(!user.isUnlocked());
        return userRepository.save(user);
    }

    // Delete user
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Find user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
