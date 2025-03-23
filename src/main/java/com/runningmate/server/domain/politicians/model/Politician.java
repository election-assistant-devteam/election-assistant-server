package com.runningmate.server.domain.politicians.model;


import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

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

    @Column
    private String imageUrl;

    @OneToOne(mappedBy = "politician", cascade = CascadeType.ALL, orphanRemoval = true)
    private PoliticianDetail politicianDetail;

    @OneToMany(mappedBy = "politician", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates = new ArrayList<>();


    @Builder
    public Politician(String party, String name) {
        this.party = party;
        this.name = name;
    }

    public void setPoliticianDetail(PoliticianDetail politicianDetail) {
        this.politicianDetail = politicianDetail;
    }

    public static Politician from(CandidateItem item) {
        return Politician.builder()
                .name(item.getName())
                .party(item.getJdName())
                .build();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
