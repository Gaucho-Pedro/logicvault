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
@Table(name = "art_portfolio")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "description"/*, columnDefinition = "TEXT"*/)
    String description;

    @Column(name = "score1")
    Integer score1;

    @Column(name = "score2")
    Integer score2;

    @Column(name = "score3")
    Integer score3;

    @Column(name = "showreel_tags"/*, columnDefinition = "TEXT"*/)
    String showreelTags;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    Grade grade;

    @Column(name = "payment_per_hour")
    Long paymentPerHour;

    @Column(name = "payment_per_day")
    Long paymentPerDay;

    @Column(name = "payment_per_month")
    Long paymentPerMonth;

    @Column(name = "payment_per_project")
    Long paymentPerProject;

    @OneToOne
    @JoinColumn(name = "payment_currency_id", referencedColumnName = "id")
    Currency currency;

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
//    @JsonIgnore
    Customer customer;

    @ManyToMany
    @JoinTable(
            name = "art_portfolio_software",
            joinColumns = @JoinColumn(name = "portfolio_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id"))
    Set<Software> software = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "art_portfolio_occupation",
            joinColumns = @JoinColumn(name = "portfolio_id"),
            inverseJoinColumns = @JoinColumn(name = "occupation_id"))
    Set<Occupation> occupation = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    Set<MediaData> mediaData = new HashSet<>();

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
