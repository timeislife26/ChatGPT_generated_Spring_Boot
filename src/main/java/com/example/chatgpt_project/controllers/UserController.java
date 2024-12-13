package com.example.chatgpt_project.controllers;

import com.example.chatgpt_project.model.User;
import com.example.chatgpt_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // Edit user (reset password)
    @PutMapping("/{id}/password")
    public ResponseEntity<User> editUserPassword(@PathVariable Long id, @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.editUserPassword(id, newPassword));
    }

    // Edit user (toggle unlocked status)
    @PutMapping("/{id}/unlock")
    public ResponseEntity<User> toggleUserUnlock(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleUserUnlock(id));
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Get a user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
