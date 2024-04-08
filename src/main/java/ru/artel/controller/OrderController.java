package ru.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artel.entity.Order;
import ru.artel.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {

    OrderService orderService;

    @PostMapping("/contractor/{id}")
    public ResponseEntity<Order> createOrderForContractorById(@PathVariable("id") Long id, @RequestBody Order order) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createByContractorId(order, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.update(order, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("id") Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

