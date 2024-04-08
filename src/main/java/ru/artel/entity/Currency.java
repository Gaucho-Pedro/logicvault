package ru.artel.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "art_currency")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Short id;

    @Column(name = "abbreviation")
    String abbreviation;
    @Column(name = "description")
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Currency currency = (Currency) o;
        return getId() != null && Objects.equals(getId(), currency.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
