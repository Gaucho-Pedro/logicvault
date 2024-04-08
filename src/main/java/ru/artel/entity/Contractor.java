package ru.artel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "art_contractor")
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contractor {

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

    @OneToMany
    @JoinColumn(name = "contractor_id")
    Set<Order> orders = new HashSet<>();

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
