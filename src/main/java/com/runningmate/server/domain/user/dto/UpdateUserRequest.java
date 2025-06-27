package com.runningmate.server.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String nickname;
    private String password;
    private String partyOfInterest;
    private String politicianOfInterest;
}
