package com.runningmate.server.domain.politicians.dto.external.nationassembly;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaRow {
    @JsonProperty("NAAS_CD")
    private String naasCd;

    @JsonProperty("NAAS_NM")
    private String naasNm;

    @JsonProperty("NAAS_CH_NM")
    private String naasChNm;

    @JsonProperty("NAAS_EN_NM")
    private String naasEnNm;

    @JsonProperty("BIRDY_DIV_CD")
    private String birdyDivCd;

    @JsonProperty("BIRDY_DT")
    private String birdyDt;

    @JsonProperty("DTY_NM")
    private String dtyNm;

    @JsonProperty("PLPT_NM")
    private String plptNm;

    @JsonProperty("ELECD_NM")
    private String elecdNm;

    @JsonProperty("ELECD_DIV_NM")
    private String elecdDivNm;

    @JsonProperty("CMIT_NM")
    private String cmitNm;

    @JsonProperty("BLNG_CMIT_NM")
    private String blngCmitNm;

    @JsonProperty("RLCT_DIV_NM")
    private String rlctDivNm;

    @JsonProperty("GTELT_ERACO")
    private String gtelTEraco;

    @JsonProperty("NTR_DIV")
    private String ntrDiv;

    @JsonProperty("NAAS_TEL_NO")
    private String naasTelNo;

    @JsonProperty("NAAS_EMAIL_ADDR")
    private String naasEmailAddr;

    @JsonProperty("NAAS_HP_URL")
    private String naasHpUrl;

    @JsonProperty("AIDE_NM")
    private String aideNm;

    @JsonProperty("CHF_SCRT_NM")
    private String chfScrtNm;

    @JsonProperty("SCRT_NM")
    private String scrtNm;

    @JsonProperty("BRF_HST")
    private String brfHst;

    @JsonProperty("OFFM_RNUM_NO")
    private String offmRnumNo;

    @JsonProperty("NAAS_PIC")
    private String naasPic;
}
