package kaczuch.master_thesis.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Dorm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "dorms")
    private Set<Organization> organizations;

    @OneToMany(mappedBy = "dorm")
    private Set<User> users = new HashSet<>();


    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}