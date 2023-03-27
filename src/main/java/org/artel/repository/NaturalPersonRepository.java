package org.artel.repository;

import org.artel.entity.NaturalPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaturalPersonRepository extends JpaRepository<NaturalPerson, Long> {
}