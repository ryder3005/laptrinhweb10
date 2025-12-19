package com.baitap10.controller;


import com.baitap10.entity.User;
import com.baitap10.models.LoginResponse;
import com.baitap10.models.LoginUserModel;
import com.baitap10.models.RegisterUserModel;
import com.baitap10.service.AuthenticationService;
import com.baitap10.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<User> register(@RequestBody RegisterUserModel registerUser) {
        User registeredUser = authenticationService.signup(registerUser);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserModel loginUser) {
        // 1. Xác thực thông tin người dùng
        User authenticatedUser = authenticationService.authenticate(loginUser);

        // 2. Tạo Token JWT
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // 3. Tạo Response trả về cho Client
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}