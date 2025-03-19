package com.runningmate.server.domain.politicians.service;

import com.runningmate.server.domain.politicians.client.NationalAssemblyApiClient;
import com.runningmate.server.domain.politicians.dto.external.nationassembly.NaRow;
import com.runningmate.server.domain.politicians.model.Politician;
import com.runningmate.server.domain.politicians.utils.PoliticianUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.runningmate.server.domain.politicians.utils.DateUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NationalAssemblyService {

    private final NationalAssemblyApiClient nationalAssemblyApiClient;
    private final PoliticianUtil politicianUtil;

    public void saveMemberImg(){

        // 1. 국회 api를 통해서 정보를 가져온다.
        List<NaRow> naRows = nationalAssemblyApiClient.fetchNaInfo();
        log.info("naRow size {}", naRows.size());

        // 2. 기존 DB에서 정치인 정보 가져오기
        // 국회 API에서 가져온 생일과 이름 기준으로 필요한 정치인만 가져오기
        List<Date> birthdays = naRows.stream()
                .map(naRow -> convertDateType(naRow.getBirdyDt()))
                .filter(Objects::nonNull) // 변환 실패한 경우 제외
                .collect(Collectors.toList());
        List<String> names = naRows.stream().map(NaRow::getNaasNm).collect(Collectors.toList());

        List<Politician> politicians = politicianUtil.findPoliticiansByBirthdayAndParty(birthdays, names);

        // 3. 기존 db에 생일과 소속당을 기반으로 국회의원 imgUrl을 추가시킨다.
        politicianUtil.updateImageUrl(politicians, naRows);
        politicianUtil.checkImageUrls(); // 디버깅용
    }

}
