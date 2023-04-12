package org.artel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "art_customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @OneToOne(cascade = {CascadeType.ALL}/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @OneToOne(cascade = {CascadeType.ALL}/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "legal_person_id", referencedColumnName = "id")
    LegalPerson legalPerson;

    @OneToOne(cascade = CascadeType.ALL/*, mappedBy = "contractor"*/)
    @JoinColumn(name = "natural_person_id", referencedColumnName = "id")
    NaturalPerson naturalPerson;

    @OneToMany/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "customer_id")
    Set<Portfolio> portfolios = new HashSet<>();

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
