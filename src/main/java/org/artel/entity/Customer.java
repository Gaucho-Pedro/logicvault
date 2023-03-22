package org.artel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "art_customer")
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
}
