package ru.artel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.Software;

import java.util.Optional;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
    Optional<Software> findByName(String name);
}