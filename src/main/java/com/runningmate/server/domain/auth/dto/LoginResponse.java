package com.runningmate.server.domain.auth.dto;

import com.runningmate.server.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String access;
    private String refresh;
    private String nickname;
    private String politicianOfInterest;
    private String partyOfInterest;

    public static LoginResponse from(String accessToken, String refreshToken, User user) {
        return LoginResponse.builder()
                .access(accessToken)
                .refresh(refreshToken)
                .nickname(user.getNickname())
                .politicianOfInterest(user.getPoliticianOfInterest())
                .partyOfInterest(user.getPartyOfInterest())
                .build();
    }
}
