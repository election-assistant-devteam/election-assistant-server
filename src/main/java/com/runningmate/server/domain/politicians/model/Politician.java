package com.runningmate.server.domain.politicians.model;


import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
public class Politician extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String party;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String electionId;
    @Column(length = 50, nullable = false)
    private String electionType;

    @OneToOne(mappedBy = "politician", cascade = CascadeType.ALL, orphanRemoval = true)
    private PoliticianDetail politicianDetail;

    @Builder
    public Politician(String party, String name, String electionId, String electionType) {
        this.party = party;
        this.name = name;
        this.electionId = electionId;
        this.electionType = electionType;
    }

    public void setPoliticianDetail(PoliticianDetail politicianDetail) {
        this.politicianDetail = politicianDetail;
    }

    public static Politician from(CandidateItem item) {
        return Politician.builder()
                .name(item.getName())
                .party(item.getJdName())
                .electionId(item.getSgId())
                .electionType(item.getSgTypecode())
                .build();
    }
}
