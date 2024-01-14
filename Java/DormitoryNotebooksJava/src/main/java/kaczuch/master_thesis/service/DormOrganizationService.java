package kaczuch.master_thesis.service;


import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.Organization;
import kaczuch.master_thesis.repositories.DormRepository;
import kaczuch.master_thesis.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DormOrganizationService {

    private final DormRepository dormRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public DormOrganizationService(DormRepository dormRepository, OrganizationRepository organizationRepository) {
        this.dormRepository = dormRepository;
        this.organizationRepository = organizationRepository;

    }

    @Transactional
    public void addOrganizationToDorm(Integer dormId, Integer organizationId) {
        Dorm dorm = dormRepository.findById(dormId).orElseThrow(() -> new RuntimeException("Dorm not found"));
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization not found"));

        // Check if the organization is already associated with the dorm
        if (!dorm.getOrganizations().contains(organization)) {
            dorm.getOrganizations().add(organization);
            // Save the updated dorm entity
            dormRepository.save(dorm);
        } else {
            // Handle the scenario where the association already exists
        }
    }
}