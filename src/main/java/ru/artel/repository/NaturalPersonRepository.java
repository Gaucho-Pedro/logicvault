package ru.artel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.NaturalPerson;

public interface NaturalPersonRepository extends JpaRepository<NaturalPerson, Long> {
}