package com.runningmate.server.domain.politicians.model;

import com.runningmate.server.domain.politicians.dto.external.candidateinfo.CandidateItem;
import com.runningmate.server.domain.politicians.utils.DateUtil;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
public class PoliticianDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Date birth;
    @Column(nullable = false)
    private String habitation;
    @Column(nullable = false)
    private String family;
    @Column(nullable = false)
    private String levelOfEducation;
    @Column(nullable = false)
    private String career;
    @Column(nullable = false)
    private String pastCrime;
    @Column(nullable = false)
    private String pledge;
    @Column(nullable = false)
    private String detail;

    @OneToOne
    @JoinColumn(name = "politician_id", referencedColumnName = "id", nullable = false)
    private Politician politician;


    @Builder
    public PoliticianDetail(Politician politician, Integer age, Date birth, String habitation, String family, String levelOfEducation, String career, String pastCrime, String pledge, String detail) {
        this.politician = politician;
        this.age = age;
        this.birth = birth;
        this.habitation = habitation;
        this.family = family;
        this.levelOfEducation = levelOfEducation;
        this.career = career;
        this.pastCrime = pastCrime;
        this.pledge = pledge;
        this.detail = detail;
    }

    public static PoliticianDetail from(Politician politician, CandidateItem item) {
        DateUtil dateUtil = new DateUtil();
        Date date = dateUtil.convertDateType(item.getBirthday());
        StringBuilder career = new StringBuilder();
        career.append(item.getCareer1()).append('\n');
        career.append(item.getCareer2()).append('\n');

        return PoliticianDetail.builder()
                .politician(politician)
                .age(item.getAge())
                .birth(date)
                .habitation(item.getAddr())
                .family("")
                .levelOfEducation(item.getEdu())
                .career(career.toString())
                .pastCrime("")
                .pledge("")
                .detail("")
                .build();
    }
}
