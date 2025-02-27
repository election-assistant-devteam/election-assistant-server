package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.PoliticianDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticianDetailRepository extends JpaRepository<PoliticianDetail, Long> {
}
