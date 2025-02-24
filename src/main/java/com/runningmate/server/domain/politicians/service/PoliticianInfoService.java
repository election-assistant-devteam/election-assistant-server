package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.model.Candidate;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.model.PoliticianDetail;
import com.runningmate.server.domain.politicians.repository.CandidateRepository;
import com.runningmate.server.domain.politicians.repository.ElectionRepository;
import com.runningmate.server.domain.politicians.repository.PoliticianDetailRepository;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import com.runningmate.server.domain.politicians.utils.CandidateUtil;
import com.runningmate.server.domain.politicians.utils.DateUtil;
import com.runningmate.server.domain.politicians.utils.ElectionCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoliticianInfoService {

    private final ElectionCodeUtil electionCodeUtil;
    private final CandidateUtil candidateUtil;
    private final PoliticianRepository politicianRepository;
    private final PoliticianDetailRepository politicianDetailRepository;
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;


    public void savePoliticianInfos() {
        // 선거 코드 가져오기
        // 번호(num), 선거id(sgId), 선거이름(sgName), 선거코드(sgTypecode), 선거날짜(sgVotedate)
        List<ElectionCodeItem> response = electionCodeUtil.fetchElectionCodes();
        log.info("response {}", response.size());

        // 필터링
        List<ElectionCodeItem> filteredByNationalAndYear = electionCodeUtil.getFilteredElectionCodeItems(response, 2024, "국회의원");
        log.info("filteredByNationalAndYear {}", filteredByNationalAndYear.size());

        for (ElectionCodeItem electionCodeItem : filteredByNationalAndYear) {
            Election election = Election.from(electionCodeItem);
            electionRepository.save(election);
        }

        // 선거 Id와 선거 종류 코드로 후보자 정보 가져오기
        List<CandidateItem> allCandidateItems = candidateUtil.fetchCandidateInfo(filteredByNationalAndYear);
        log.info("allCandidateItems {}", allCandidateItems.size());

        // DB 저장

        for (CandidateItem allCandidateItem : allCandidateItems) {
            Politician politician = Politician.from(allCandidateItem);
            PoliticianDetail politicianDetail = PoliticianDetail.from(politician, allCandidateItem);

            politician.setPoliticianDetail(politicianDetail);
            politicianRepository.save(politician);

            Optional<Election> foundElection = electionRepository.findByDateAndType(DateUtil.convertDateType(allCandidateItem.getSgId()), allCandidateItem.getSgTypecode());

            Election election = foundElection.get();
            // 🔹 Candidate(후보자) 저장 (중복 방지)
            Candidate candidate = Candidate.from(politician, election, allCandidateItem.getJdName());
            candidateRepository.save(candidate);
        }
    }
}
