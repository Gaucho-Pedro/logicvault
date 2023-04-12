package org.artel.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "art_software")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "name")
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Software software = (Software) o;
        return getId() != null && Objects.equals(getId(), software.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
