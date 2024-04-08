package ru.artel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.LegalPerson;

public interface LegalPersonRepository extends JpaRepository<LegalPerson, Long> {
}