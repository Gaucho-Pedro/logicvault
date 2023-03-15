package org.artel.controller;

import lombok.RequiredArgsConstructor;
import org.artel.entity.Customer;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping("/{id}/legal")
    public Customer registerAsLegalPersonByContractorId(@PathVariable("id") Long id,
                                                        @Valid @RequestBody LegalPerson legalPerson) {
        return customerService.registerAsLegalPersonByCustomerId(id, legalPerson);
    }

    @PostMapping("/{id}/natural")
    public Customer registerAsNaturalPersonByContractorId(@PathVariable("id") Long id,
                                                          @Valid @RequestBody NaturalPerson naturalPerson) {
        return customerService.registerAsNaturalPersonByCustomerId(id, naturalPerson);
    }

    @PostMapping("/auth/register")
    public User createCustomer(@Valid @RequestBody User user) {
        return customerService.createCustomer(user);
    }
}
