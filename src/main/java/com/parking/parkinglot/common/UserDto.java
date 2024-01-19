
        package com.parking.parkinglot.common;

        import com.parking.parkinglot.entities.Car;

public class UserDto {
    String email;
    String username;
    Long id;

    public UserDto(Long id, String username, String email) {
        this.id = id;
        this.email = email;
        this.username=username;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}