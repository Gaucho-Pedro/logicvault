package ru.artel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artel.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}