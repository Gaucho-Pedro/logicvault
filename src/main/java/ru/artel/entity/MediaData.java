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
@Table(name = "art_media_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "url")
    String url;
    @Column(name = "tags")
    String tags;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MediaData mediaData = (MediaData) o;
        return getId() != null && Objects.equals(getId(), mediaData.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
