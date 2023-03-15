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

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "contractor")
    private LegalPerson legalPerson;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "contractor")
    private NaturalPerson naturalPerson;
}
