package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.dto.internal.CandidateSimpleInfo;
import com.runningmate.server.domain.politicians.dto.internal.CandidatesResponse;
import com.runningmate.server.domain.politicians.model.Candidate;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.repository.CandidateRepository;
import com.runningmate.server.domain.politicians.repository.ElectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;

    public CandidatesResponse fetchElectionInfo(Long electionId, Integer lastId){
        // 선거 ID로 선거 찾기
        Optional<Election> fetchedElection = electionRepository.findById(electionId);

        if(fetchedElection.isEmpty())
            return null;

        Election election = fetchedElection.get();
        log.info("election {}", election.getName());

        // 선거 후보자들 찾기
        List<Politician> collect = findElectionCandidates(election);

        // DTO 생성
        ArrayList<CandidateSimpleInfo> candidatesSimpleInfo = getCandidateSimpleInfos(collect);

        // 범위에 맞게 후보자 가져오기
        return getPaginatedCandidates(lastId, candidatesSimpleInfo, 10);

    }

    public List<Politician> findElectionCandidates(Election election) {
        List<Candidate> candidates = candidateRepository.findByElection(election);
        List<Politician> collect = candidates.stream()
                .map(Candidate::getPolitician)
                .toList();
        return collect;
    }

    public ArrayList<CandidateSimpleInfo> getCandidateSimpleInfos(List<Politician> collect) {
        ArrayList<CandidateSimpleInfo> candidatesSimpleInfo = new ArrayList<>();
        for (Politician politician : collect) {
            CandidateSimpleInfo candidateSimpleInfo = CandidateSimpleInfo.builder()
                    .id(politician.getId().intValue())
                    .name(politician.getName())
                    .party(politician.getParty())
                    .imageUrl(politician.getImageUrl())
                    .build();
            candidatesSimpleInfo.add(candidateSimpleInfo);


            log.info("politician {}", politician.getName());
            log.info("politician {}", politician.getParty());
        }
        return candidatesSimpleInfo;
    }

    private CandidatesResponse getPaginatedCandidates(int lastId, List<CandidateSimpleInfo> all, int pageSize) {
        boolean isMore = true;
        ArrayList<CandidateSimpleInfo> subList;

        if (lastId >= all.size()) {
            subList = new ArrayList<>();
            isMore = false;
        } else {
            int toIndex = Math.min(lastId + pageSize, all.size());
            subList = new ArrayList<>(all.subList(lastId, toIndex));
            isMore = toIndex < all.size();
            lastId = toIndex;
        }

        return CandidatesResponse.builder()
                .candidates(subList)
                .lastId(lastId)
                .hasMore(isMore)
                .build();
    }

}
