package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.model.PoliticianDetail;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PoliticianUtil {
    private final PoliticianRepository politicianRepository;

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
}
