package com.runningmate.server.domain.politicians.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeResponseBody;
import com.runningmate.server.domain.politicians.dto.external.common.CommonApiResponse;
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


@Slf4j
@RequiredArgsConstructor
public class ElectionCodeUtil {

    public static List<ElectionCodeItem> getFilteredElectionCodeItems(List<ElectionCodeItem> response,
                                                               Integer minYear,
                                                               String keyword) {
        List<ElectionCodeItem> filteredByNationalAndYear = new ArrayList<>();
        for (var electionCodeItem : response) {
            Integer year = Integer.parseInt(electionCodeItem.getSgVotedate().substring(0, 4));
            if (year >= minYear
                    && electionCodeItem.getSgName().contains(keyword)) {
                filteredByNationalAndYear.add(electionCodeItem);
            }
        }
        return filteredByNationalAndYear;
    }
}
