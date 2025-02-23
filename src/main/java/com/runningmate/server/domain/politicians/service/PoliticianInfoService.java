package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.utils.ElectionCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoliticianInfoService {

    private final ElectionCodeUtil electionCodeUtil;

    public void savePoliticianInfos() {
        // 선거 코드 가져오기
        // 번호(num), 선거id(sgId), 선거이름(sgName), 선거코드(sgTypecode), 선거날짜(sgVotedate)
        List<ElectionCodeItem> response = electionCodeUtil.fetchElectionCodes();
        log.info("response {}", response.size());

        // 필터링
        List<ElectionCodeItem> filteredByNationalAndYear = electionCodeUtil.getFilteredElectionCodeItems(response);
        log.info("filteredByNationalAndYear {}", filteredByNationalAndYear.size());

    }
}
