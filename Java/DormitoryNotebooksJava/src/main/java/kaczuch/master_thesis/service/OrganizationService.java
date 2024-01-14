package kaczuch.master_thesis.service;

import kaczuch.master_thesis.model.Organization;
import kaczuch.master_thesis.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Optional<Organization> findByAcronym(String acronym) {
        return organizationRepository.findByAcronym(acronym);
    }
}
