package kaczuch.master_thesis.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "int")
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ItemToRent item;

    private LocalDate rentalDate;
    private LocalTime rentHour;
    private LocalTime returnHour;

    public Rental(User user, ItemToRent item, LocalDate rentalDate, LocalTime rentHour, LocalTime returnHour) {
        this.user = user;
        this.item = item;
        this.rentalDate = rentalDate;
        this.rentHour = rentHour;
        this.returnHour = returnHour;
    }

    public Rental() {
        super();
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItemToRent getItem() {
        return item;
    }

    public void setItem(ItemToRent item) {
        this.item = item;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalTime getRentHour() {
        return rentHour;
    }

    public void setRentHour(LocalTime rentHour) {
        this.rentHour = rentHour;
    }

    public LocalTime getReturnHour() {
        return returnHour;
    }

    public void setReturnHour(LocalTime returnHour) {
        this.returnHour = returnHour;
    }
}
