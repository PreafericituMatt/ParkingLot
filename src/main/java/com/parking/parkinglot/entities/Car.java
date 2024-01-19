package com.parking.parkinglot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Car {
    private String licensePlate;
    private Long id;
    private String parkingSpot;
    private CarPhoto photo;
    @OneToOne(mappedBy = "car",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public CarPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(CarPhoto photo) {
        this.photo = photo;
    }
    @Size(min=3,max=100)
    @Column(unique = true,nullable = false,length = 100)
    @Basic
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Size(min=3,max=100)
    @Column(unique = true,nullable = false,length = 100)
    public String getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(String parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    private User owner;

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}