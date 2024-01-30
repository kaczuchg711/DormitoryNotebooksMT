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

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public boolean isUserAssignedToDorm(Integer userId, Integer dormID) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Dorm dorm = organizationRepository.findById(dormID).orElseThrow(() -> new EntityNotFoundException ("Dorm not found"));
        user.getDorms();
        user.getDorms().contains(dorm);
        return user.getDorms().contains(dorm);
    }

    @Transactional
    public void addUserToDorm(Integer userId, Integer dormID) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Dorm dorm = organizationRepository.findById(dormID).orElseThrow(() -> new RuntimeException("Dorm not found"));

        if (!user.getDorms().contains(dorm)) {
            user.getDorms().add(dorm);
            userRepository.save(user);
        } else {
            logger.warn("\n\n\nUser {} already added to dorm {}\n\n\n", userId, dormID);
        }
    }

    @Transactional(readOnly = true)
    public List<Integer> getDormIdsForUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Assuming the User entity has a method getDorms()
        // that returns a collection of Dorm entities
        return user.getDorms().stream()
                .map(Dorm::getId) // Extracts the ID of each Dorm
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Dorm> getDormsForUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Assuming the User entity has a method getDorms()
        // that returns a collection of Dorm entities
        return user.getDorms().stream().toList();
    }
}