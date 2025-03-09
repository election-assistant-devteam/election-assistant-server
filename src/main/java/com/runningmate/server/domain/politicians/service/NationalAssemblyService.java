package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.dto.external.nationassembly.NaRow;
import com.runningmate.server.domain.politicians.utils.NaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NationalAssemblyService {

    private final NaUtil naUtil;

    public void saveMemberImg(){

        // 1. 기존 db에 있는 후보자들을 가져온다.

        // 2. 국회 api를 통해서 정보를 가져온다.

        List<NaRow> naRows = naUtil.fetchNaInfo();
        log.info("naRow size {}", naRows.size());


        // Json을 객체로 변환

        // 3. 기존 db에 생일과 소속당을 기반으로 국회의원 imgUrl을 추가시킨다.
    }
}
