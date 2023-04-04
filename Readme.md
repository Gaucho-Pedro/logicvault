```java
@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ContractorController {

    ContractorService contractorService;
    ModelMapper modelMapper;

    @GetMapping("/v1/{id}")
    public Contractor getContractorById(@PathVariable("id") Long id) {
        return contractorService.findById(id);
    }

    @GetMapping("/v1/user/{userId}")
    public Contractor getContractorByUserId(@PathVariable("userId") Long userId) {
        return contractorService.findByUserId(userId);
    }

    @GetMapping("/v1")
    public List<Contractor> getContractors() {
        return contractorService.getContractors();
    }

    @PostMapping("/v1/{id}/legal")
    public ContractorDto setLegalStatus(@PathVariable("id") Long id,
                                        @Valid @RequestBody LegalPerson legalPerson) {
        return modelMapper.map(contractorService.setLegalStatus(id, legalPerson), ContractorDto.class);
    }

    @PostMapping("/v1/{id}/natural")
    public ContractorDto setNaturalStatus(@PathVariable("id") Long id,
                                          @Valid @RequestBody NaturalPerson naturalPerson) {
        return modelMapper.map(contractorService.setNaturalStatus(id, naturalPerson), ContractorDto.class);
    }

    @PostMapping("/v1/auth/register")
    public ResponseEntity<ContractorDto> createContractor(@Valid @RequestBody User user) {
        return new ResponseEntity<>(modelMapper.map(contractorService.createContractor(user), ContractorDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/v1/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody User user) {
        return contractorService.signInContractor(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/v1/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        contractorService.deleteContractorById(id);
    }
}
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerController {

    CustomerService customerService;
    ModelMapper modelMapper;

    @GetMapping("/v1/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/v1/user/{userId}")
    public Customer getCustomerByUserId(@PathVariable("userId") Long userId) {
        return customerService.findByUserId(userId);
    }

    @GetMapping("/v1")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping("/v1/{id}/legal")
    public CustomerDto setLegalStatus(@PathVariable("id") Long id,
                                      @Valid @RequestBody LegalPerson legalPerson) {
        return modelMapper.map(customerService.setLegalStatus(id, legalPerson), CustomerDto.class);
    }

    @PostMapping("/v1/{id}/natural")
    public CustomerDto setNaturalStatus(@PathVariable("id") Long id,
                                        @Valid @RequestBody NaturalPerson naturalPerson) {
        return modelMapper.map(customerService.setNaturalStatus(id, naturalPerson), CustomerDto.class);
    }

    @PostMapping("/v1/auth/register")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody User user) {
        return new ResponseEntity<>(modelMapper.map(customerService.createCustomer(user), CustomerDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/v1/auth/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody User user) {
        return customerService.signInCustomer(user) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/v1/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        customerService.deleteCustomerById(id);
    }
    
    @GetMapping("/v1/{id}/portfolios")
    public ResponseEntity<Set<Portfolio>> getPortfoliosForCustomerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getPortfoliosForCustomerById(id));
    }
}
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {

    OrderService orderService;

    @PostMapping("/v1/contractor/{id}")
    public ResponseEntity<Order> createOrderForContractorById(@PathVariable("id") Long id, @RequestBody Order order) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createOrderForContractorById(order, id));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        return ResponseEntity.ok(orderService.updateOrder(order, id));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }
}

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioController {

    PortfolioService portfolioService;

    @PostMapping("/v1/customer/{id}")
    public ResponseEntity<Portfolio> createPortfolioForCustomerById(@PathVariable("id") Long id, @RequestBody Portfolio portfolio) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(portfolioService.createPortfolioForCustomerById(portfolio, id));
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(portfolioService.findById(id));
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable("id") Long id, @RequestBody Portfolio portfolio) {
        return ResponseEntity.ok(portfolioService.updatePortfolio(portfolio, id));
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<?> deletePortfolioById(@PathVariable("id") Long id) {
        portfolioService.deletePortfolioById(id);
        return ResponseEntity.noContent().build();
    }
}
```
Dto и Entity:
```java
@Data
public class ContractorDto {

    private Long id;
    private Long userId;
    private LegalPerson legalPerson;
    private NaturalPerson naturalPerson;
}
@Data
public class CustomerDto {

    private Long id;
    private Long userId;
    private LegalPerson legalPerson;
    private NaturalPerson naturalPerson;
}
@Getter
@Setter
@Entity
@Table(name = "art_activity_type")
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ActivityType that = (ActivityType) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
Getter
@Setter
@Entity
@Table(name = "art_contractor")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL}/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = {CascadeType.ALL}/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "legal_person_id", referencedColumnName = "id")
    private LegalPerson legalPerson;

    @OneToOne(cascade = CascadeType.ALL/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "natural_person_id", referencedColumnName = "id")
    private NaturalPerson naturalPerson;

    @OneToMany
    @JoinColumn(name = "contractor_id")
    private Set<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Contractor that = (Contractor) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
Getter
@Setter
@Entity
@Table(name = "art_customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL}/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = {CascadeType.ALL}/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "legal_person_id", referencedColumnName = "id")
    private LegalPerson legalPerson;

    @OneToOne(cascade = CascadeType.ALL/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "natural_person_id", referencedColumnName = "id")
    private NaturalPerson naturalPerson;

    @OneToMany/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "customer_id")
    private Set<Portfolio> portfolios;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
@Getter
@Setter
@Entity
@Table(name = "art_legal_person")
public class LegalPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "inn")
    private String inn;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "legal_address", columnDefinition = "TEXT")
    private String legalAddress;

    /*    @JsonIgnore
        @OneToOne
        @JoinColumn(name = "contractor_id", referencedColumnName = "id")
        private Contractor contractor;

        @JsonIgnore
        @OneToOne
        @JoinColumn(name = "customer_id", referencedColumnName = "id")
        private Customer customer;*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LegalPerson that = (LegalPerson) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
@Getter
@Setter
@Entity
@Table(name = "art_natural_person")
public class NaturalPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "passport_data", columnDefinition = "TEXT")
    private String passportData;

/*    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private Contractor contractor;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NaturalPerson that = (NaturalPerson) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

@Entity
@Table(name = "art_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @ManyToOne
    @JoinColumn(name = "contractor_id")
    @JsonIgnore
    private Contractor contractor;
}

@Entity
@Table(name = "art_portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "score1")
    private int score1;

    @Column(name = "score2")
    private int score2;

    @Column(name = "score3")
    private int score3;

    @Column(name = "showreel_tags", columnDefinition = "TEXT")
    private String showreelTags;

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "art_portfolio_software",
            joinColumns = @JoinColumn(name = "portfolio_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id"))
    private Set<Software> software;

}

@Entity
@Table(name = "art_software")
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
}
@Entity
@Table(name = "art_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User /*implements UserDetails*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Email
    @Column
    private String email;
    //    @Pattern
    @Column(name = "phone_number")
    private String phoneNumber;

}
```