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
@Component
@RequiredArgsConstructor
public class PoliticianUtil {
    private final PoliticianRepository politicianRepository;

    public void updateImageUrl(List<Politician> politicians, List<NaRow> naRows) {
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

    public List<Politician> findAllPoliticians() {
        List<Politician> politicians = politicianRepository.findAll();
        return politicians;
    }

    public Politician savePolitician(CandidateItem allCandidateItem) {
        if (checkDuplicatePolitician(allCandidateItem.getName(), allCandidateItem.getJdName())) return null;
        Politician politician = Politician.from(allCandidateItem);
        PoliticianDetail politicianDetail = PoliticianDetail.from(politician, allCandidateItem);
        politician.setPoliticianDetail(politicianDetail);
        politicianRepository.save(politician);
        return politician;
    }

    private boolean checkDuplicatePolitician(String name, String party) {
        Optional<Politician> foundPolitician = politicianRepository.findByNameAndParty(name, party);
        if(foundPolitician.isPresent()) {
            return true;
        }
        return false;
    }

    // 디버깅용
    public void checkImageUrls() {
        List<Politician> politicians = politicianRepository.findAll();
        for (Politician politician : politicians) {
            log.info("이름: {}, 정당: {}, 이미지 URL: {}",
                    politician.getName(), politician.getParty(), politician.getImageUrl());
        }
    }

}
