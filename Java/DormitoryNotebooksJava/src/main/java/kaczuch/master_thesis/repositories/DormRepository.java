package kaczuch.master_thesis.repositories;

import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DormRepository extends JpaRepository<Dorm, Integer> {

    Optional<Dorm> findByName(String name);
    Optional<Dorm> findById(Integer id);

    List<Dorm> findByUsers_Id(Integer userId);
}
