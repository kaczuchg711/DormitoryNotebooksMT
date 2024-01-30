package kaczuch.master_thesis.model;

import jakarta.persistence.*;

@Entity
public class ItemToRent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( columnDefinition = "int")
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    private Integer number;

    private boolean isAvailable;


    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

}
