package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Candidate;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByPoliticianAndElection(Politician politician, Election election);

    List<Candidate> findByElection(Election election);
}
