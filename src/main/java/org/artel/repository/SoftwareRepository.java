package org.artel.repository;

import org.artel.entity.Software;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
    Optional<Software> findByName(String name);
}