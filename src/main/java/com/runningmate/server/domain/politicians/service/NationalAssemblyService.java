package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.dto.external.nationassembly.NaRow;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.model.PoliticianDetail;
import com.runningmate.server.domain.politicians.repository.PoliticianDetailRepository;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import com.runningmate.server.domain.politicians.utils.DateUtil;
import com.runningmate.server.domain.politicians.utils.NaUtil;
import com.runningmate.server.domain.politicians.utils.PoliticianUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NationalAssemblyService {

    private final NaUtil naUtil;
    private final PoliticianUtil politicianUtil;

    public void saveMemberImg(){

        // 1. 기존 DB에서 정치인 정보 가져오기
        List<Politician> politicians = politicianUtil.findAllPoliticians();

        // 2. 국회 api를 통해서 정보를 가져온다.
        List<NaRow> naRows = naUtil.fetchNaInfo();
        log.info("naRow size {}", naRows.size());

        // 3. 기존 db에 생일과 소속당을 기반으로 국회의원 imgUrl을 추가시킨다.
        politicianUtil.updateImageUrl(politicians, naRows);
        politicianUtil.checkImageUrls(); // 디버깅용
    }

}
