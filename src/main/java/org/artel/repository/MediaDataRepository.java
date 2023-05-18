package org.artel.repository;

import org.artel.entity.MediaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaDataRepository extends JpaRepository<MediaData, Long> {
}