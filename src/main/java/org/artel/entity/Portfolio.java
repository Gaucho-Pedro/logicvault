package org.artel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "art_portfolio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "score1")
    int score1;

    @Column(name = "score2")
    int score2;

    @Column(name = "score3")
    int score3;

    @Column(name = "showreel_tags", columnDefinition = "TEXT")
    String showreelTags;

    //    @ManyToMany
//    @JoinTable(
//            name = "art_portfolio_activity",
//            joinColumns = @JoinColumn(name = "portfolio_id"),
//            inverseJoinColumns = @JoinColumn(name = "activity_id"))
//    private Set<ActivityType> activityTypes;
    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    Customer customer;

    @ManyToMany
    @JoinTable(
            name = "art_portfolio_software",
            joinColumns = @JoinColumn(name = "portfolio_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id"))
    Set<Software> software = new HashSet<>();

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
