package com.baitap10.service;

import com.baitap10.entity.UserInfo;
import com.baitap10.repository.UserInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {

    public String addUser(UserInfo userInfo) {
        // Mã hóa mật khẩu trước khi lưu vào DB
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        repository.save(userInfo);
        return "Thêm user thành công!";
    }
}