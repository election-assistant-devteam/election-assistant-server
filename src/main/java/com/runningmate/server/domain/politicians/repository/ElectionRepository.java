package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    Optional<Election> findByDateAndType(LocalDate date, String type);
}
