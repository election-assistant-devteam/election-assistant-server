package com.runningmate.server.domain.politicians.controller.internal;

import com.runningmate.server.domain.politicians.dto.internal.PoliticianResponse;
import com.runningmate.server.domain.politicians.service.PoliticianService;
import com.runningmate.server.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PoliticianController {
    private final PoliticianService politicianService;

    @GetMapping("/politicians/{politicianId}/detail")
    public BaseResponse<Object> getPoliticianDetail(@PathVariable Long politicianId, @RequestParam(required = false) Integer lastId){
        PoliticianResponse politicianResponse = politicianService.getPoliticianDetail(politicianId);
        return new BaseResponse<>(politicianResponse);
    }
}
