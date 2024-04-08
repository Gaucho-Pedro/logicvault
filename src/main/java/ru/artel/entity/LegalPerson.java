package ru.artel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.math.BigInteger;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "art_legal_person")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LegalPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "inn")
    BigInteger inn;

    @Column(name = "registration_number")
    BigInteger registrationNumber;

    @Column(name = "legal_address", columnDefinition = "TEXT")
    String legalAddress;

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
