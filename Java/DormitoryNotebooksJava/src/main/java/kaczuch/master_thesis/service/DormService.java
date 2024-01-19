package kaczuch.master_thesis.service;

import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.Organization;
import kaczuch.master_thesis.repositories.DormRepository;
import kaczuch.master_thesis.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DormService {

    @Autowired
    private DormRepository dormRepository;

    public Optional<Dorm> getDormByName(String name) {
        return dormRepository.findByName(name);
    }
}
