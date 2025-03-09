package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.dto.external.nationassembly.NaRow;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.model.PoliticianDetail;
import com.runningmate.server.domain.politicians.repository.PoliticianDetailRepository;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import com.runningmate.server.domain.politicians.utils.DateUtil;
import com.runningmate.server.domain.politicians.utils.NaUtil;
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
    private final PoliticianRepository politicianRepository;

    public void saveMemberImg(){

        // 1. 기존 DB에서 정치인 정보 가져오기
        List<Politician> politicians = politicianRepository.findAll();
        // 2. 기존 DB에서 정치인 상세정보 가져오기

        // 3. 국회 api를 통해서 정보를 가져온다.
        List<NaRow> naRows = naUtil.fetchNaInfo();
        log.info("naRow size {}", naRows.size());

        // 4. 기존 db에 생일과 소속당을 기반으로 국회의원 imgUrl을 추가시킨다.
        for (Politician politician : politicians) {
            PoliticianDetail detail = politician.getPoliticianDetail(); // 상세 정보 가져오기

            for (NaRow naRow : naRows) {
                if (politicianMatchesNaRow(politician, detail, naRow)) {
                    politician.setImageUrl(naRow.getNaasPic());
                    //log.info("{} 정치인 이미지 URL 업데이트: {}", politician.getName(), naRow.getNaasPic());
                }
            }
        }
        politicianRepository.saveAll(politicians);
        //checkImageUrls();
    }

    private boolean politicianMatchesNaRow(Politician politician, PoliticianDetail detail, NaRow naRow) {
        // 이름 비교
        boolean isNameMatch = politician.getName().equals(naRow.getNaasNm());

        // 국회 - String -> Date 변환
        Date birth2Date = DateUtil.convertDateType(naRow.getBirdyDt());
        if(birth2Date == null)
            return false;

        // 생일 비교
        boolean isBirthMatch = detail != null && detail.getBirth().compareTo(birth2Date) == 0;

        return isNameMatch && isBirthMatch;
    }

    public void checkImageUrls() {
        List<Politician> politicians = politicianRepository.findAll();
        for (Politician politician : politicians) {
            log.info("이름: {}, 정당: {}, 이미지 URL: {}",
                    politician.getName(), politician.getParty(), politician.getImageUrl());
        }
    }

}
