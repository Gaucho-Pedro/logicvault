package ru.artel.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artel.dto.CustomerDto;
import ru.artel.dto.RegisterDto;
import ru.artel.dto.SignInDto;
import ru.artel.entity.Customer;
import ru.artel.entity.LegalPerson;
import ru.artel.entity.NaturalPerson;
import ru.artel.entity.Portfolio;
import ru.artel.service.CustomerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerController {

    CustomerService customerService;


    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        return ResponseEntity.ok(mappingUtil.toDtoList(customerService.findAll(), CustomerDto.class));
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
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(mappingUtil.toDto(customerService.create(
                mappingUtil.toEntity(registerDto, Customer.class)), CustomerDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto signInDto) {
        return customerService.signIn(signInDto) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        customerService.delete(id);
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
