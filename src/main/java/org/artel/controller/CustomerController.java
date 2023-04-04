package org.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.CustomerDto;
import org.artel.dto.SignInDto;
import org.artel.entity.*;
import org.artel.service.CustomerService;
import org.artel.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerController {

    CustomerService customerService;
    MappingUtil mappingUtil;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return ResponseEntity.ok(mappingUtil.toDtoList(customerService.getCustomers(), CustomerDto.class));
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public Customer getCustomerByUserId(@PathVariable("userId") Long userId) {
        return customerService.findByUserId(userId);
    }


    @PostMapping("/{id}/legal")
    public CustomerDto setLegalStatus(@PathVariable("id") Long id,
                                      @Valid @RequestBody LegalPerson legalPerson) {
        return mappingUtil.toDto(customerService.setLegalStatus(id, legalPerson), CustomerDto.class);
    }

    @PostMapping("/{id}/natural")
    public CustomerDto setNaturalStatus(@PathVariable("id") Long id,
                                        @Valid @RequestBody NaturalPerson naturalPerson) {
        return mappingUtil.toDto(customerService.setNaturalStatus(id, naturalPerson), CustomerDto.class);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody User user) {
        return new ResponseEntity<>(mappingUtil.toDto(customerService.createCustomer(user), CustomerDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto signInDto) {
        return customerService.signInCustomer(signInDto) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        customerService.deleteCustomerById(id);
    }

//    @PostMapping("/{id}/portfolio")
//    public ResponseEntity<Customer> createPortfolio(@PathVariable("id") Long id, @RequestBody Portfolio portfolio) {
//        return ResponseEntity.ok(customerService.createPortfolioForCustomerById(portfolio, id));
//    }

    @GetMapping("/{id}/portfolios")
    public ResponseEntity<Set<Portfolio>> getPortfoliosForCustomerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getPortfoliosForCustomerById(id));
    }
}
