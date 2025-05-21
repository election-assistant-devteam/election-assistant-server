package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Candidate;
import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.politicians.model.Politician;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByPoliticianAndElection(Politician politician, Election election);

    @Query("""
        SELECT c FROM Candidate c
        WHERE c.election = :election
          AND c.politician.id > :lastId
        ORDER BY c.politician.id ASC
    """)
    List<Candidate> findNextCandidates(
            @Param("election") Election election,
            @Param("lastId") Integer lastId,
            Pageable pageable
    );
}
