package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.nationassembly.NaRow;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.model.PoliticianDetail;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class PoliticianUtil {

    public static boolean politicianMatchesNaRow(Politician politician, NaRow naRow) {

        // 이름 비교
        boolean isNameMatch = politician.getName().equals(naRow.getNaasNm());

        // 국회 - String -> Date 변환
        Date birth2Date = DateUtil.convertDateType(naRow.getBirdyDt());
        if(birth2Date == null)
            return false;

        // 생일 비교
        PoliticianDetail politicianDetail = politician.getPoliticianDetail();
        boolean isBirthMatch = politicianDetail != null && DateUtil.compareDate(politicianDetail.getBirth(),birth2Date);

        return isNameMatch && isBirthMatch;
    }






}
