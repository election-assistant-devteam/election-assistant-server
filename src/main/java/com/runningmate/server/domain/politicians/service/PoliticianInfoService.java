package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.client.CandidateApiClient;
import com.runningmate.server.domain.politicians.client.ElectionCodeApiClient;
import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.repository.ElectionRepository;
import com.runningmate.server.domain.politicians.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoliticianInfoService {

    private final ElectionCodeApiClient electionCodeApiClient;
    private final CandidateApiClient candidateApiClient;

    private final CandidateUtil candidateUtil;
    private final PoliticianUtil politicianUtil;

    private final ElectionRepository electionRepository;

    public void savePoliticianInfos() {
        // 선거 코드 가져오기
        // 번호(num), 선거id(sgId), 선거이름(sgName), 선거코드(sgTypecode), 선거날짜(sgVotedate)
        List<ElectionCodeItem> response = electionCodeApiClient.fetchElectionCodes();
        log.info("response {}", response.size());

        // 필터링
        List<ElectionCodeItem> filteredByNationalAndYear = ElectionCodeUtil.getFilteredElectionCodeItems(response, 2024, "국회의원");
        log.info("filteredByNationalAndYear {}", filteredByNationalAndYear.size());

        // Election 저장 (중복 방지)
        for (ElectionCodeItem electionCodeItem : filteredByNationalAndYear) {
            saveElection(electionCodeItem);
        }

        // 선거 Id와 선거 종류 코드로 후보자 정보 가져오기
        List<CandidateItem> allCandidateItems = candidateApiClient.fetchCandidateInfo(filteredByNationalAndYear);
        log.info("allCandidateItems {}", allCandidateItems.size());

        // DB 저장
        for (CandidateItem allCandidateItem : allCandidateItems) {
            // Politician 저장 (중복 방지)
            Politician politician = politicianUtil.savePolitician(allCandidateItem);
            if (politician == null) continue;

            Election election = findElectionByCandidateItem(allCandidateItem);
            // Candidate(후보자) 저장 (중복 방지)
            candidateUtil.saveCandidate(allCandidateItem, politician, election);
        }
    }

    public Election findElectionByCandidateItem(CandidateItem allCandidateItem) {
        Optional<Election> foundElection = electionRepository.findByDateAndType(DateUtil.convertDateType(allCandidateItem.getSgId()), allCandidateItem.getSgTypecode());
        Election election = foundElection.get();
        return election;
    }

    public void saveElection(ElectionCodeItem electionCodeItem) {
        if (checkDuplicateElection(electionCodeItem.getSgVotedate(), electionCodeItem.getSgTypecode())) return;
        Election election = Election.from(electionCodeItem);
        electionRepository.save(election);
    }

    private boolean checkDuplicateElection(String electionId, String electionCode) {
        Date electionDate = DateUtil.convertDateType(electionId);
        Optional<Election> foundElection = electionRepository.findByDateAndType(electionDate, electionCode);
        if(foundElection.isPresent()) return true;
        return false;
    }
}
