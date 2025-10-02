package com.runningmate.server.domain.politicians.init;

import com.runningmate.server.domain.politicians.service.NationalAssemblyService;
import com.runningmate.server.domain.politicians.service.PoliticianInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PoliticianInitializer implements ApplicationRunner {

    private final PoliticianInfoService politicianInfoService;
    private final NationalAssemblyService nationalAssemblyService;

    @Override
    public void run(ApplicationArguments args){
        try{
            politicianInfoService.savePoliticianInfos();
            log.info("기본 저장 완료");

            nationalAssemblyService.saveMemberImg();
            log.info("정치인 사진 저장 완료");
        }
        catch(Exception e){
            log.error("API 호출 실패 {}", e.getMessage());
        }
    }

}
