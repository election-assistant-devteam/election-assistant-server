package com.runningmate.server.domain.politicians.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLDelete(sql = "UPDATE user SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
public class Candidate {
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
}
