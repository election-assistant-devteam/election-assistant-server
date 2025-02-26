package com.runningmate.server.domain.politicians.model;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.dto.external.electioncode.ElectionCodeItem;
import com.runningmate.server.domain.politicians.utils.DateUtil;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
public class Election extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String type;
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates = new ArrayList<>();

    @Builder
    public Election(String name, Date date, String type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public static Election from(ElectionCodeItem item) {
        DateUtil dateUtil = new DateUtil();
        Date date = dateUtil.convertDateType(item.getSgVotedate());

        return Election.builder()
                .name(item.getSgName())
                .date(date)
                .type(item.getSgTypecode())
                .build();
    }
}
