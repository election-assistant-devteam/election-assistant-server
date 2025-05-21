package com.runningmate.server.domain.politicians.controller.internal;

import com.runningmate.server.domain.politicians.dto.internal.CandidatesResponse;
import com.runningmate.server.domain.politicians.service.CandidateService;
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
public class CandidateController {
    private final CandidateService candidateService;

    @GetMapping("/elections/{electionId}/candidates")
    public BaseResponse<Object> getCandidates(@PathVariable Long electionId, @RequestParam Integer lastId){
        CandidatesResponse candidatesResponse = candidateService.fetchElectionInfo(electionId, lastId);
        return new BaseResponse<>(candidatesResponse);
    }
}
