package org.artel.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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
