package ru.artel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.MediaData;

public interface MediaDataRepository extends JpaRepository<MediaData, Long> {
}