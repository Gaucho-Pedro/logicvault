package ru.artel.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.Contractor;

import java.util.List;
import java.util.Optional;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    @Override
    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    List<Contractor> findAll();

    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    List<Contractor> findByLegalPersonNotNullAndNaturalPersonNull();

    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    List<Contractor> findByNaturalPersonNotNullAndLegalPersonNull();

    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    Optional<Contractor> findByUserId(Long userId);
}