package com.parking.parkinglot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Collection;

@Entity
public class User {
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    private String username;
    @Column(unique = true,nullable = false,length = 100)
    @Basic
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String email;
    private String password;
    @Email
    @Column(unique = true,nullable = false,length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Collection<Car> cars;

    @OneToMany(mappedBy = "owner")

    public Collection<Car> getCars() {
        return cars;
    }

    public void setCars(Collection<Car> cars) {
        this.cars = cars;
    }
}
