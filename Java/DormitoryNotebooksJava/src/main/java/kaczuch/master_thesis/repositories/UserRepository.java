package kaczuch.master_thesis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kaczuch.master_thesis.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail (String email);
}
