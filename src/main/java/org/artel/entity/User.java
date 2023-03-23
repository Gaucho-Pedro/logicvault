package org.artel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity
@Table(name = "art_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User /*implements UserDetails*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Email
    @Column
    private String email;
    //    @Pattern
    @Column(name = "phone_number")
    private String phoneNumber;

/*    @ManyToOne
    @JoinColumn(name = "user_type")
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "person_type")
    private PersonType personType;*/
/*    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }*/
}