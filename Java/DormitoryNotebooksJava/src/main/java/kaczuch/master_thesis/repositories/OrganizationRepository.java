package kaczuch.master_thesis.repositories;

import kaczuch.master_thesis.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    Optional<Organization> findByAcronym(String organizationAcronym);
    Optional<Organization> findById(Long id);

    // Find organizations by user
    List<Organization> findByUsers_Id(Long userId);
}
