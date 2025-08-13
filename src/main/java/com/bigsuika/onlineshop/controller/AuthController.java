package com.bigsuika.onlineshop.controller;

import com.bigsuika.onlineshop.model.User;
import com.bigsuika.onlineshop.repository.UserRepository;
import com.bigsuika.onlineshop.security.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User API", description = "Login API")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Parameter(description = "username and password", required = true)
                                        @RequestBody LoginRequest loginRequest){
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!user.getPassword().equals(user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }

        String token = jwtTokenUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(token);
    }

    @Getter
    @Setter
    private static class LoginRequest {
        private String username;
        private String password;
    }
}
