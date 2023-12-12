package com.erp.backend.entities;


import com.erp.backend.entities.base.AuditableBase;
import com.erp.backend.enums.Role;
import com.erp.backend.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
//@SQLDelete(sql = "UPDATE user SET isDeleted = true WHERE id = ?")
//@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = true)
public class User extends AuditableBase implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String phone;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)

    private String email;
    @Size(min = 8, message = "Minimum password length: 8 characters")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "user")
    private List<FavoriteArticle> favoriteArticles;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    //    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
       // return List.of(new SimpleGrantedAuthority(role.name()));
      //  authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
     //   return authorities;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }


}