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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        if(fetchedElection.isEmpty()) return null;
        Election election = fetchedElection.get();
        log.info("election {}", election.getName());

        // 페이지네이션 - 후보자 가져오기
        Integer cursor = (lastId != null) ? lastId : 0;
        int size = 10; // 고정 페이징 크기
        Pageable pageable = PageRequest.of(0, size); // page는 0으로 해야함, size는 limit size 역할, offset은 page*size
        List<Candidate> candidates = candidateRepository.findNextCandidates(election, cursor, pageable);

        // 간단 후보자 정보로 변환
        List<CandidateSimpleInfo> simpleInfos = mapToCandidateSimpleInfos(candidates);

        // lastID 생성
        Integer newLastId = getLastIdBySimpleInfos(simpleInfos, cursor);

        return CandidatesResponse.builder()
                .candidates(simpleInfos)
                .lastId(newLastId)
                .hasMore(simpleInfos.size() == size)
                .build();

    }

    private static List<CandidateSimpleInfo> mapToCandidateSimpleInfos(List<Candidate> candidates) {
        List<CandidateSimpleInfo> simpleInfos = candidates.stream()
                .map(c -> {
                    Politician p = c.getPolitician();
                    return CandidateSimpleInfo.builder()
                            .id(p.getId().intValue())
                            .name(p.getName())
                            .party(p.getParty())
                            .imageUrl(p.getImageUrl())
                            .build();
                }).toList();
        return simpleInfos;
    }

    private static Integer getLastIdBySimpleInfos(List<CandidateSimpleInfo> simpleInfos, Integer cursor) {
        Integer newLastId = simpleInfos.isEmpty()
                ? cursor
                : simpleInfos.get(simpleInfos.size() - 1).getId();
        return newLastId;
    }

}
