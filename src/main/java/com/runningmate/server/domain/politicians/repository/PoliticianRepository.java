package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticianRepository extends JpaRepository<Politician, Long> {
}
