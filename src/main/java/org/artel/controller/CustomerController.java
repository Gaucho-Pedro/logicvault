package org.artel.controller;

import lombok.RequiredArgsConstructor;
import org.artel.entity.Customer;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public Customer getCustomerByUserId(@PathVariable("userId") Long userId) {
        return customerService.findByUserId(userId);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping("/{id}/legal")
    public Customer setLegalStatus(@PathVariable("id") Long id,
                                   @Valid @RequestBody LegalPerson legalPerson) {
        return customerService.setLegalStatus(id, legalPerson);
    }

    @PostMapping("/{id}/natural")
    public Customer setNaturalStatus(@PathVariable("id") Long id,
                                     @Valid @RequestBody NaturalPerson naturalPerson) {
        return customerService.setNaturalStatus(id, naturalPerson);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody User user) {
        return new ResponseEntity<>(customerService.createCustomer(user), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody User user) {
        return customerService.signInCustomer(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
