package ru.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.artel.dto.SignInDto;
import ru.artel.entity.*;
import ru.artel.repository.CustomerRepository;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerService {

    UserService userService;
    //    PortfolioService portfolioService;
    CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findCustomersWhichAreLegalPerson() {
        return customerRepository.findByLegalPersonNotNullAndNaturalPersonNull();
    }

    public List<Customer> findCustomersWhichAreNaturalPerson() {
        return customerRepository.findByNaturalPersonNotNullAndLegalPersonNull();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found"));
    }

    public Customer findByUserId(Long userId) {
        return customerRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with userId " + userId + " not found"));
    }

    public Customer create(Customer newCustomer) {
        if (userService.findByUsername(newCustomer.getUser().getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Username: " + newCustomer.getUser().getUsername() + " is already exist");
        }
        if (newCustomer.getLegalPerson() != null && newCustomer.getNaturalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer cannot be a legal person and an natural person at the same time");
        }
        var user = userService.create(newCustomer.getUser());
        newCustomer.setUser(user);
        return customerRepository.save(newCustomer);
    }

    public boolean signIn(SignInDto signInDto) {
        User byUsername = userService.findByUsername(signInDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + signInDto.getUsername() + " not found"));
        findByUserId(byUsername.getId());
        return userService.signIn(signInDto.getPassword(), byUsername.getPassword());
    }

    public Customer setLegalStatus(Long id, LegalPerson legalPerson) {
        Customer customer = findById(id);
        if (customer.getNaturalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer with id: " + id + " is already a natural person");
        }
        if (customer.getLegalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer with id: " + id + " is already a legal person");
        }
//        legalPerson.setCustomer(customer);
        customer.setLegalPerson(legalPerson);
        return customerRepository.save(customer);
    }

    public Customer setNaturalStatus(Long id, NaturalPerson naturalPerson) {
        Customer customer = findById(id);
        if (customer.getLegalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer with id: " + id + " is already a legal person");
        }
        if (customer.getNaturalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer with id: " + id + " is already a natural person");
        }
//        naturalPerson.setCustomer(customer);
        customer.setNaturalPerson(naturalPerson);
        return customerRepository.save(customer);
    }

//    public Customer createPortfolioForCustomerById(Portfolio portfolio, Long customerId) {
//        Customer customer = findById(customerId);
//        customer.getPortfolios().add(portfolioService.createPortfolio(portfolio));
//        return customerRepository.save(customer);
//    }

    public Set<Portfolio> getPortfoliosForCustomerById(Long customerId) {
        return findById(customerId).getPortfolios();
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
