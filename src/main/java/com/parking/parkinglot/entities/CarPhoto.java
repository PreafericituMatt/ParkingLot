package com.parking.parkinglot.entities;

import jakarta.persistence.*;

@Entity
public class CarPhoto {
    private Long id;

    @GeneratedValue
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String filename;

    @Basic
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String fileType;
    Car car;

    @Basic
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    private byte[] fileContent;

    @Basic
    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }
    @OneToOne
    public Car getCar(){return car;}

    public void setCar(Car car) {
        this.car=car;
    }
}