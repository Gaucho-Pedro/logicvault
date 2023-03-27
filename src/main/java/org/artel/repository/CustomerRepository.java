package org.artel.repository;

import org.artel.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Override
    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    List<Customer> findAll();

    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    List<Customer> findByLegalPersonNotNullAndNaturalPersonNull();

    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    List<Customer> findByNaturalPersonNotNullAndLegalPersonNull();

    @EntityGraph(attributePaths = {"legalPerson", "naturalPerson"})
    Optional<Customer> findByUserId(Long userId);
}