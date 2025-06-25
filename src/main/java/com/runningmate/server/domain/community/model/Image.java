package com.runningmate.server.domain.community.model;

import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Long imageOrder;

    @ManyToOne
    private Post post;
}
