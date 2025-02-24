package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectionUtil {
    private final ElectionRepository electionRepository;

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
