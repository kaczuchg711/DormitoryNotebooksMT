package kaczuch.master_thesis.repositories;

import kaczuch.master_thesis.model.Breakdown;
import kaczuch.master_thesis.model.Dorm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface BreakdownRepository extends JpaRepository<Breakdown, Integer> {
    Optional<Breakdown> findById(Integer id);
}