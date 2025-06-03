package com.runningmate.server.domain.politicians.dto.internal;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PoliticianResponse {
    private Long politicianId;
    private String politicianName;
    private String party;
    private String imageUrl;
    private Integer age;
    private LocalDate birth;
    private String habitation;
    private String family;
    private String levelOfEducation;
    private String career;
    private String pastCrime;
    private String pledge;
    private String detail;
}
