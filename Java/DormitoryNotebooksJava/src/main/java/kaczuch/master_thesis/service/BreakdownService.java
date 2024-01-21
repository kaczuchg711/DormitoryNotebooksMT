package kaczuch.master_thesis.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import kaczuch.master_thesis.model.Breakdown;
import kaczuch.master_thesis.repositories.BreakdownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BreakdownService {

    private final BreakdownRepository breakdownRepository;

    // Using constructor injection is a good practice
    @Autowired
    public BreakdownService(BreakdownRepository breakdownRepository) {
        this.breakdownRepository = breakdownRepository;
    }

    // Method to retrieve all breakdowns from the database
    public List<Breakdown> findAll() {
        return breakdownRepository.findAll();
    }

    // Method to retrieve a breakdown by its ID
    public Optional<Breakdown> findById(Long id) {
        return breakdownRepository.findById(id.intValue());
    }

    // Method to save a new breakdown
    public Breakdown save(Breakdown breakdown) {
        return breakdownRepository.save(breakdown);
    }

    // Method to delete a breakdown by its ID
    public void deleteById(Long id) {
        breakdownRepository.deleteById(id.intValue());
    }

    // Add more methods as needed for your application logic
}
