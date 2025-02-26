package com.runningmate.server.domain.politicians.model;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@SQLDelete(sql = "UPDATE user SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
public class Candidate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "politician_id", nullable = false)
    private Politician politician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @Column(name = "affiliated_party", length = 50, nullable = false)
    private String affiliatedPartyAtTheTime;

    @Builder
    public Candidate(Politician politician, Election election, String pastParty) {
        this.politician = politician;
        this.election = election;
        this.affiliatedPartyAtTheTime = pastParty;
    }

    public static Candidate from(Politician politician, Election election, String pastParty) {
        return Candidate.builder()
                .politician(politician)
                .election(election)
                .pastParty(pastParty) // 출마 당시 정당
                .build();
    }
}
