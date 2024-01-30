package kaczuch.master_thesis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kaczuch.master_thesis.model.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail (String email);

	List<User> findByOrganizations_Id(Integer organizationId);

	List<User> findByDorms_Id(Integer dormId);

}
