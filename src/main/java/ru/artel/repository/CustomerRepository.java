package ru.artel.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.Customer;

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