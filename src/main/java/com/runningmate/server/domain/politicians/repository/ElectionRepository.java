package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    Optional<Election> findByDateAndType(Date date, String type);
}
