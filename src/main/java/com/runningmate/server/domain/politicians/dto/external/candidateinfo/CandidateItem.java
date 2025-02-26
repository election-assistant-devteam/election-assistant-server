package com.runningmate.server.domain.politicians.dto.external.candidateinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateItem {
    @JsonProperty("num")
    private int num;

    @JsonProperty("sgId")
    private String sgId;

    @JsonProperty("sgTypecode")
    private String sgTypecode;

    @JsonProperty("huboid")
    private String huboid;

    @JsonProperty("sggName")
    private String sggName;

    @JsonProperty("sdName")
    private String sdName;

    @JsonProperty("wiwName")
    private String wiwName;

    @JsonProperty("giho")
    private String giho;

    @JsonProperty("gihoSangse") // 필요하면 추가
    private String gihoSangse;

    @JsonProperty("jdName")
    private String jdName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("hanjaName")
    private String hanjaName;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("age")
    private int age;

    @JsonProperty("addr")
    private String addr;

    @JsonProperty("jobId")
    private String jobId;

    @JsonProperty("job")
    private String job;

    @JsonProperty("eduId")
    private String eduId;

    @JsonProperty("edu")
    private String edu;

    @JsonProperty("career1")
    private String career1;

    @JsonProperty("career2")
    private String career2;

    @JsonProperty("status")
    private String status;
}
