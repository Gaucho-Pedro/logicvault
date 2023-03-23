package org.artel.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.artel.entity.Customer;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerService {

    UserService userService;
    CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomersWhichAreLegalPerson() {
        return customerRepository.findByLegalPersonNotNullAndNaturalPersonNull();
    }

    public List<Customer> getCustomersWhichAreNaturalPerson() {
        return customerRepository.findByNaturalPersonNotNullAndLegalPersonNull();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id " + id + " not found"));
    }

    public Customer findByUserId(Long userId) {
        return customerRepository.findByUserId(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with userId " + userId + " not found"));
    }

    public Customer createCustomer(User newUser) {
        if (userService.findByUsername(newUser.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Username: " + newUser.getUsername() + " is already exist");
        }
        var user = userService.addUser(newUser);
        Customer customer = new Customer();
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    public boolean signInCustomer(User user) {
        User byUsername = userService.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + user.getUsername() + " not found"));
        findByUserId(byUsername.getId());
        return userService.signIn(user.getPassword(), byUsername.getPassword());
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
    public void deleteCustomerById(Long id){
        customerRepository.deleteById(id);
    }
}
