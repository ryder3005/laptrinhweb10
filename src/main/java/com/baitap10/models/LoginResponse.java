package com.baitap10.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;

    // Lưu ý: @Data đã bao gồm Getter/Setter, không cần viết lại getToken() thủ công.
}