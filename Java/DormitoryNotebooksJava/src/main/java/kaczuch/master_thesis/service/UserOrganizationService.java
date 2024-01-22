package kaczuch.master_thesis.service;


import kaczuch.master_thesis.model.Organization;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.repositories.OrganizationRepository;
import kaczuch.master_thesis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class UserOrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(UserOrganizationService.class);

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public UserOrganizationService(UserRepository userRepository, OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;

    }

    @Transactional
    public void addUserToOrganization(Long userId, Long organizationId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Dorm not found"));
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization not found"));

        // Check if the organization is already associated with the dorm
        if (!user.getOrganizations().contains(organization)) {
            user.getOrganizations().add(organization);
            // Save the updated dorm entity
            userRepository.save(user);
        } else {
            logger.warn("\n\n\nUser {} already added to organization {}\n\n\n", userId, organizationId);
        }
    }
}