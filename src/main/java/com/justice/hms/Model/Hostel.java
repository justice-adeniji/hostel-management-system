package com.justice.hms.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "hostels")
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Hostel name cannot be blank")
    @Size(max = 100, message = "Hostel name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Hostel location cannot be blank")
    @Size(max = 200, message = "Hostel location cannot exceed 200 characters")
    private String location;

    @NotNull(message = "Number of rooms cannot be null")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    @Max(value = 1000, message = "Number of rooms cannot exceed 1000")
    private Integer numberOfRooms;

    @NotNull(message = "Room capacity cannot be null")
    @Min(value = 2, message = "Room capacity must be at least 2")
    @Max(value = 6, message = "Room capacity cannot exceed 6")
    private Integer roomCapacity;

    public Hostel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}