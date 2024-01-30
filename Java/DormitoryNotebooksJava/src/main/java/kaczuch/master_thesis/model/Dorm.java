package kaczuch.master_thesis.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Dorm {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column( columnDefinition = "int")
    private Integer id;
    @Column( length = 60)
    private String name;
    @ManyToMany(mappedBy = "dorms")
    private Set<Organization> organizations;
    @ManyToMany(mappedBy = "dorms")
    private Set<User> users = new HashSet<>();

    public Set<Organization> getOrganizations() {
        return organizations;
    }


    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
        organization.getDorms().add(this);
    }

    public void removeOrganization(Organization organization) {
        organizations.remove(organization);
        organization.getDorms().remove(this);
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

}