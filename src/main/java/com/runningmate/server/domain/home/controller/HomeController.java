package com.runningmate.server.domain.home.controller;

import com.runningmate.server.domain.home.dto.GetHomeResponse;
import com.runningmate.server.domain.home.service.HomeService;
import com.runningmate.server.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("home")
@RestController
public class HomeController {
    private final HomeService homeService;
    @GetMapping
    public BaseResponse<GetHomeResponse> getHome(){
        log.info("[getHome]");
        return new BaseResponse(homeService.getData(3, 5));
    }
}
