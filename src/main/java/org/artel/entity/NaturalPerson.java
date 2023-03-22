package org.artel.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
}
