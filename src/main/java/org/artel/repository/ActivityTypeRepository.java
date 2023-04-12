package org.artel.repository;

import org.artel.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {
}