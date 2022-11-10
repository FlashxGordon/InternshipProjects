package com.sa22.LMASpringData.security.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roleEntities;

    public UserEntity() {
    }

    public UserEntity(String username, String password, Collection<GrantedAuthority> authorities) {
    }

    public UserEntity(String username, String password, String email, Set<RoleEntity> roleEntities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleEntities = roleEntities;
    }

    public UserEntity(long id, String username, String password, String email, Set<RoleEntity> roleEntities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleEntities = roleEntities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roleEntities;
    }

    public void setRoles(Set<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity userEntity = (UserEntity) o;
        return id == userEntity.id && username.equals(userEntity.username) && password.equals(userEntity.password) && email.equals(userEntity.email) && roleEntities.equals(userEntity.roleEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, roleEntities);
    }
}
