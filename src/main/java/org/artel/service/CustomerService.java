package org.artel.service;

import lombok.RequiredArgsConstructor;
import org.artel.entity.Customer;
import org.artel.entity.LegalPerson;
import org.artel.entity.NaturalPerson;
import org.artel.entity.User;
import org.artel.repository.CustomerRepository;
import org.artel.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getContractorsWhichAreLegalPerson() {
        return customerRepository.findByLegalPersonNotNullAndNaturalPersonNull();
    }

    public List<Customer> getContractorsWhichAreNaturalPerson() {
        return customerRepository.findByNaturalPersonNotNullAndLegalPersonNull();
    }

    public User createCustomer(User user) {
//        Optional<User> byUsernameOrEmail = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        Optional<User> byUsernameOrEmail = userRepository.findByUsername(user.getUsername());
        if (byUsernameOrEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Username: " + user.getUsername() + " is already exist");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            Customer customer = new Customer();
            customer.setUserId(user.getId());
            customerRepository.save(customer);
            return user;
        }
    }

    public Customer registerAsLegalPersonByCustomerId(Long id, LegalPerson legalPerson) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        if (customer.getNaturalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer with id: " + id + " is already a natural entity");
        }
        legalPerson.setCustomer(customer);
        customer.setLegalPerson(legalPerson);
        return customerRepository.saveAndFlush(customer);
    }

    public Customer registerAsNaturalPersonByCustomerId(Long id, NaturalPerson naturalPerson) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        if (customer.getLegalPerson() != null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer with id: " + id + " is already a legal entity");
        }
        naturalPerson.setCustomer(customer);
        customer.setNaturalPerson(naturalPerson);
        return customerRepository.saveAndFlush(customer);
    }
}
