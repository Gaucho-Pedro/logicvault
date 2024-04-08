package ru.artel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.Occupation;

import java.util.Optional;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {

    Optional<Occupation> findByDescription(String description);

}