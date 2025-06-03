package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.dto.internal.PoliticianResponse;
import com.runningmate.server.domain.politicians.exception.PoliticianNotFoundException;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.model.PoliticianDetail;
import com.runningmate.server.domain.politicians.repository.PoliticianDetailRepository;
import com.runningmate.server.domain.politicians.repository.PoliticianRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.runningmate.server.global.common.response.status.BaseExceptionResponseStatus.FIND_POLITICIAN_FAILED;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoliticianService {

    private final PoliticianRepository politicianRepository;
    private final PoliticianDetailRepository politicianDetailRepository;

    public PoliticianResponse getPoliticianDetail(Long politicianId) {
        Politician politician = politicianRepository.findById(politicianId)
                .orElseThrow(() -> new PoliticianNotFoundException(FIND_POLITICIAN_FAILED));
        log.info(politician.toString());

        return PoliticianResponse.builder()
                .politicianId(politician.getId())
                .politicianName(politician.getName())
                .party(politician.getParty())
                .imageUrl(politician.getImageUrl())
                .age(politician.getPoliticianDetail().getAge())
                .birth(politician.getPoliticianDetail().getBirth())
                .habitation(politician.getPoliticianDetail().getHabitation())
                .family(politician.getPoliticianDetail().getFamily())
                .levelOfEducation(politician.getPoliticianDetail().getLevelOfEducation())
                .career(politician.getPoliticianDetail().getLevelOfEducation())
                .pastCrime(politician.getPoliticianDetail().getPastCrime())
                .pledge(politician.getPoliticianDetail().getPledge())
                .detail(politician.getPoliticianDetail().getDetail())
                .build();
    }
}
