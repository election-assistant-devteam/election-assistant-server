package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
