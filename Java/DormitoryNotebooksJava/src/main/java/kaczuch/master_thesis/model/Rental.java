package kaczuch.master_thesis.model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ItemToRent item;

    private LocalDate rentalDate;
    private LocalTime rentHour;
    private LocalTime returnHour;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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
