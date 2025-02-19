package com.runningmate.server.domain.auth.dto;

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
}
