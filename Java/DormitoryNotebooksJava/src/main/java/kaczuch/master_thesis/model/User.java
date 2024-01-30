package kaczuch.master_thesis.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( columnDefinition = "int")
    private Integer id;
    private String email;
    private String password;
    @Column(length = 30)
    private String role;
    @Column(length = 30)
    private String first_name;
    @Column(length = 150)
    private String last_name;
    @Column(columnDefinition = "VARCHAR(10)")
    private Integer roomNumber;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    @ManyToMany
    @JoinTable(
            name = "user_organization",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    private Set<Organization> organizations = new HashSet<>();
    ;
    @ManyToMany
    @JoinTable(
            name = "user_dorm",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dorm_id")
    )
    private Set<Dorm> dorms = new HashSet<>();
    ;

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public Set<Dorm> getDorms() {
        return dorms;
    }

    public void setDorms(Set<Dorm> dorms) {
        this.dorms = dorms;
    }

    public User() {
        super();
    }

    public User(String email, String password, String role, String first_name, String last_name) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;


















        
    }

    public User(Integer id, String email, String password, String role, String first_name, String last_name, Set<Organization> organizations, Set<Dorm> dorms) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.organizations = organizations;
        this.dorms = dorms;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
