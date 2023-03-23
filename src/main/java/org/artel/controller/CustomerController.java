package org.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.CustomerDto;
import org.artel.entity.Customer;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerController {

    CustomerService customerService;
    ModelMapper modelMapper;

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
    public CustomerDto setLegalStatus(@PathVariable("id") Long id,
                                      @Valid @RequestBody LegalPerson legalPerson) {
        return modelMapper.map(customerService.setLegalStatus(id, legalPerson), CustomerDto.class);
    }

    @PostMapping("/{id}/natural")
    public CustomerDto setNaturalStatus(@PathVariable("id") Long id,
                                        @Valid @RequestBody NaturalPerson naturalPerson) {
        return modelMapper.map(customerService.setNaturalStatus(id, naturalPerson), CustomerDto.class);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody User user) {
        return new ResponseEntity<>(modelMapper.map(customerService.createCustomer(user), CustomerDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody User user) {
        return customerService.signInCustomer(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        customerService.deleteCustomerById(id);
    }
}
