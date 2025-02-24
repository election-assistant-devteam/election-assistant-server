package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
