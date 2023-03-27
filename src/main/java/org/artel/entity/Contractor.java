package org.artel.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
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
