package org.artel.repository;

import org.artel.entity.Contractor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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