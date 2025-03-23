package com.runningmate.server.domain.politicians.repository;

import com.runningmate.server.domain.politicians.model.Politician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PoliticianRepository extends JpaRepository<Politician, Long> {
    Optional<Politician> findByNameAndParty(String name, String party);

    @Query("SELECT p FROM Politician p " +
            "JOIN p.politicianDetail d " +
            "WHERE d.birth IN :birthdays " +
            "AND p.name IN :names")
    List<Politician> findPoliticiansByBirthdayAndName(
            @Param("birthdays") List<Date> birthdays,
            @Param("names") List<String> names
    );
}
