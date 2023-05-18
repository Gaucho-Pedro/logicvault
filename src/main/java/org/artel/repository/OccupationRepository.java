package org.artel.repository;

import org.artel.entity.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {

    Optional<Occupation> findByDescription(String description);

}