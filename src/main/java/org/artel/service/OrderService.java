package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.entity.Contractor;
import org.artel.entity.Order;
import org.artel.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderService {

    ContractorService contractorService;
    OrderRepository orderRepository;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with id " + id + " not found"));
    }

    public Order createOrderForContractorById(Order order, Long contractorId) {
        Contractor contractor = contractorService.findById(contractorId);
        order.setContractor(contractor);
        return orderRepository.save(order);
    }

    public Order updateOrder(Order newOrder, Long id) {
        Order order = findById(id);

        order.setDescription(newOrder.getDescription());
        order.setTargetDate(newOrder.getTargetDate());

        //TODO выставление типа активности
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}

