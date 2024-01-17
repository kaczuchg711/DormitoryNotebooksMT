package kaczuch.master_thesis.service;

import jakarta.persistence.EntityNotFoundException;
import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.repositories.DormRepository;
import kaczuch.master_thesis.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDormService {
    private static final Logger logger = LoggerFactory.getLogger(UserDormService.class);

    private final UserRepository userRepository;
    private final DormRepository organizationRepository;

    @Autowired
    public UserDormService(UserRepository userRepository, DormRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;

    }

    public boolean isUserAssignedToDorm(Long userId, Integer dormID) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Dorm dorm = organizationRepository.findById(dormID).orElseThrow(() -> new EntityNotFoundException ("Dorm not found"));

        return user.getDorms().contains(dorm);
    }

    @Transactional
    public void addUserToDorm(Long userId, Integer dormID) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Dorm dorm = organizationRepository.findById(dormID).orElseThrow(() -> new RuntimeException("Dorm not found"));

        if (!user.getDorms().contains(dorm)) {
            user.getDorms().add(dorm);
            userRepository.save(user);
        } else {
            logger.warn("\n\n\nUser {} already added to dorm {}\n\n\n", userId, dormID);
        }
    }
}