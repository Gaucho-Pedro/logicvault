package org.artel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
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

//    @ManyToMany
//    @JoinTable(
//            name = "art_portfolio_activity",
//            joinColumns = @JoinColumn(name = "portfolio_id"),
//            inverseJoinColumns = @JoinColumn(name = "activity_id"))
//    private Set<ActivityType> activityTypes;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Portfolio portfolio = (Portfolio) o;
        return getId() != null && Objects.equals(getId(), portfolio.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
