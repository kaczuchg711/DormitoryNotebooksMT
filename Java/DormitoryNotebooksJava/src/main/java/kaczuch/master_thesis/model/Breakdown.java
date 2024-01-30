package kaczuch.master_thesis.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "breakdowns")
public class Breakdown {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( columnDefinition = "int")
    private Integer id;

    @Column(length = 600)
    private String description;

    @Column(name = "isSolved", columnDefinition = "TINYINT(1)")
    private Boolean isSolved;

    @ManyToOne
    @JoinColumn(name = "dorm_id", referencedColumnName = "id")
    private Dorm dorm;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "requestDate")
    private Date requestDate;

    public Breakdown(String description, Boolean isSolved, Dorm dorm, User user, Date requestDate) {
        this.description = description;
        this.isSolved = isSolved;
        this.dorm = dorm;
        this.user = user;
        this.requestDate = requestDate;
    }

    public Breakdown() {
        super();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
