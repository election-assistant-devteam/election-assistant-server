package com.runningmate.server.global.common.response.status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus{
    SUCCESS(20000, "요청에 성공했습니다."),
    BAD_REQUEST(40000, "유효하지 않은 요청입니다."),
    NOT_FOUND(40400, "존재하지 않는 API입니다."),

    UNAUTHORIZED_USER(40100,  "권한이 없는 사용자의 요청입니다."),

    // 엔티티 관련 에러
    USER_NOT_FOUND(40400, "존재하지 않는 유저입니다."),

    // auth 도메인 에러
    INCORRECT_USERNAME(40400, "아이디가 일치하는 회원이 없습니다"),
    INCORRECT_PASSWORD(40400, "비밀번호가 일치하지 않습니다"),
    LOGIN_FAILED(40400, "아이디나 비밀번호가 일치하는 회원이 없습니다."),

    // 유저 관련 에러
    SAME_USERNAME_EXISTS(40000, "동일한 아이디를 가진 사용자가 존재합니다."),
    SAME_USER_EMAIL_EXISTS(40000, "동일한 이메일을 가진 사용자가 존재합니다."),
    SAME_USER_INFO_EXISTS(40000, "동일한 정보로 가입한 유저가 존재합니다."),

    // 외부 api json 파싱 실패
    PARSING_FAILED(50000, "외부 api JSON 파싱에 실패하였습니다."),

    INTERNAL_SERVER_ERROR(50000, "서버 내부 오류입니다.");

    // 외부 api 파싱 에러


    private final boolean success = false;
    private final int code;
    private final String message;

    @Override
    public boolean getSuccess() { return this.success; }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
