package com.runningmate.server.domain.politicians.controller.external;

import com.runningmate.server.domain.politicians.service.NationalAssemblyService;
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
    private final NationalAssemblyService nationalAssemblyService;

    @GetMapping("/politicians")
    public BaseResponse<Void> savePoliticianInfoList(){
        // 기본 정보 저장
        politicianInfoService.savePoliticianInfos();
        log.info("기본 저장 완료");
        // 정치인 사진 첨부
        nationalAssemblyService.saveMemberImg();

        return new BaseResponse<>(null);
    }
}
