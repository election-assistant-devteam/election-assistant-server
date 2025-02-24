package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PoliticianRepository extends JpaRepository<Politician, Long> {
    Optional<Politician> findByNameAndParty(String name, String party);
}
