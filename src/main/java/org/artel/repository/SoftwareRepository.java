package org.artel.repository;

import org.artel.entity.Software;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
}