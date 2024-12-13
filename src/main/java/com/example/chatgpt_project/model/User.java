package com.example.chatgpt_project.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String county;

    @ElementCollection(fetch = FetchType.EAGER)
    private Collection<String> roles;

    private boolean locked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> (GrantedAuthority) () -> "ROLE_" + role).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Customize expiration logic here if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customize credential expiration if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Customize enabling logic if needed
    }

    public void setUnlocked(boolean b) {
        this.locked = b;
    }
}
