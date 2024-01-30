package kaczuch.master_thesis.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( columnDefinition = "int")
    private Integer id;
    @Column( length = 60)

    private String name;
    @Column( length = 10)
    private String acronym;

    @ManyToMany
    @JoinTable(
            name = "organization_dorm",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "dorm_id")
    )
    private Set<Dorm> dorms;

    @ManyToMany(mappedBy = "organizations")
    private Set<User> users = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public Set<Dorm> getDorms() {
        return dorms;
    }

    public void setDorms(Set<Dorm> dorms) {
        this.dorms = dorms;
    }

    // Add methods to add and remove Dorms
    public void addDorm(Dorm dorm) {
        dorms.add(dorm);
        dorm.getOrganizations().add(this);
    }

    public void removeDorm(Dorm dorm) {
        dorms.remove(dorm);
        dorm.getOrganizations().remove(this);
    }
}

