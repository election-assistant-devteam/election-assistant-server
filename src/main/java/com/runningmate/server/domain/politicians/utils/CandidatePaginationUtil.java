package com.runningmate.server.domain.politicians.utils;

import com.runningmate.server.domain.politicians.dto.internal.CandidateSimpleInfo;
import com.runningmate.server.domain.politicians.dto.internal.CandidatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
public class CandidatePaginationUtil {

    public CandidatesResponse getPaginatedCandidates(Integer lastId, ArrayList<CandidateSimpleInfo> candidatesSimpleInfo, int page) {
        if(candidatesSimpleInfo.isEmpty()){
            return CandidatesResponse.builder()
                    .candidates(new ArrayList<>())  // 빈 리스트 반환
                    .lastId(lastId)
                    .hasMore(false)
                    .build();
        }


        int toIndex = Math.min(lastId + page, candidatesSimpleInfo.size());
        log.info("lastId {}, toIndex {}", lastId, toIndex);
        ArrayList<CandidateSimpleInfo> subList = new ArrayList<>(candidatesSimpleInfo.subList(lastId, toIndex));
        boolean isMore = toIndex < candidatesSimpleInfo.size();
        int updatedLastId = toIndex-1;

        // 최대 10개씩 반환
        return CandidatesResponse.builder()
                .candidates(subList)
                .lastId(updatedLastId)
                .hasMore(isMore)
                .build();
    }
}
