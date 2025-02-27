package com.runningmate.server.domain.politicians.controller.external;

import com.runningmate.server.domain.politicians.service.PoliticianInfoService;
import com.runningmate.server.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ElectionController {

    private final PoliticianInfoService politicianInfoService;

    @GetMapping("/politicians")
    public BaseResponse<Void> savePoliticianInfoList(){
        politicianInfoService.savePoliticianInfos();

        return new BaseResponse<>(null);
    }
}
