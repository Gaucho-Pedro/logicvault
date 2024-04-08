package ru.artel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}