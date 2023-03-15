package org.artel.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "art_contractor")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contractor {

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
