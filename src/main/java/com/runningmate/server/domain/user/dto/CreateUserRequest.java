package com.runningmate.server.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CreateUserRequest {
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{9,12}$", message="아이디는 영어와 숫자의 조합으로 9자 이상의 문자열이어야합니다.")
    @NotEmpty(message = "아이디는 비어 있을 수 없습니다")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{9,12}$", message="비밀번호는 영어와 숫자의 조합으로 9자 이상의 문자열이어야합니다.")
    @NotEmpty(message = "비밀번호는 비어 있을 수 없습니다")
    private String password;

    @Email(message = "이메일 형식이 아닙니다.")
    @NotEmpty(message = "이메일은 비어 있을 수 없습니다")
    private String email;

    @Length(min=1, max=20, message = "닉네임은 길이가 1이상 20이하이어야 합니다")
    @NotEmpty(message = "닉네임은 비어 있을 수 없습니다")
    private String nickname;
}
