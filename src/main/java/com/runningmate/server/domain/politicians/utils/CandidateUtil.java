package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateResponse;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.dto.external.common.CommonApiResponse;
import com.runningmate.server.domain.politicians.model.Candidate;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class CandidateUtil {

    private final CandidateRepository candidateRepository;


    public void saveCandidate(CandidateItem allCandidateItem, Politician politician, Election election) {
        if (checkDuplicateCandidate(politician, election)) return;
        Candidate candidate = Candidate.from(politician, election, allCandidateItem.getJdName());
        candidateRepository.save(candidate);
    }

    private boolean checkDuplicateCandidate(Politician politician, Election election) {
        Optional<Candidate> foundCandidate = candidateRepository.findByPoliticianAndElection(politician, election);
        if (foundCandidate.isPresent()) {
            return true;
        }
        return false;
    }
}
