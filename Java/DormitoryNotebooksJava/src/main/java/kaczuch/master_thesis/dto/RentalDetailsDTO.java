package kaczuch.master_thesis.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RentalDetailsDTO {

    private String firstName;
    private String lastName;
    private int roomNumber;
    private LocalDate date;
    private LocalTime time;

    // Constructors, Getters, and Setters

    public RentalDetailsDTO() {
        // Default constructor
    }

    public RentalDetailsDTO(String firstName, String lastName, int roomNumber, LocalDate date, LocalTime time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}