package com.nnk.springboot.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "FullName is mandatory")
    private String fullName;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*?[a-z]).*$", message = "Must have at least one lower case letter")
    @Pattern(regexp = "^(?=.*?[a-z]).*$", message = "Must have at least one capital letter")
    @Pattern(regexp = "^(?=.*?[0-9]).*$", message = "Must have at least one number")
    @Pattern(regexp = "^(?=.*?\\W).*$", message = "Must have a special character")
    @Pattern(regexp = "^(?=.{8,32}).*$", message = "Must have between 8 and 32 characters")
    private String password;

    @NotBlank(message = "Role is mandatory")
    private String role;

    public User() {

    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
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

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {

        this.fullName = fullName;
    }

    public String getRole() {

        return role;
    }

    public void setRole(String role) {

        this.role = role;
    }

    /**
     * Retrieve static permissions from user.
     *
     * @return a {@link List} of {@link GrantedAuthority}.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getRoleName()));
        return authorities;
    }

    /**
     * Retrieve the role name of {@link User}
     *
     * @return a {@link String}
     */
    public String getRoleName() {

        return this.getRole();
    }

    /**
     * Retrieve User Information
     *
     * @return a {@link User#username}
     */
    @Override
    public String getUsername() {

        return this.username;
    }


    /**
     * Spring Security features that must be implemented but are not used for this project.
     *
     * @return static {@link Boolean} with always a true value
     */
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * Spring Security features that must be implemented but are not used for this project.
     *
     * @return static {@link Boolean} with always a true value
     */
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    /**
     * Spring Security features that must be implemented but are not used for this project.
     *
     * @return static {@link Boolean} with always a true value
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    /**
     * Spring Security features that must be implemented but are not used for this project.
     *
     * @return static {@link Boolean} with always a true value
     */
    @Override
    public boolean isEnabled() {

        return true;
    }
}
