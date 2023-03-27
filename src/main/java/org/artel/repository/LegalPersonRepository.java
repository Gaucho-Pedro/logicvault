package org.artel.repository;

import org.artel.entity.LegalPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalPersonRepository extends JpaRepository<LegalPerson, Long> {
}