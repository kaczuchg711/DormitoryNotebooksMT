package kaczuch.master_thesis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import kaczuch.master_thesis.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail (String email);

	List<User> findByOrganizations_Id(Integer organizationId);

	List<User> findByDorms_Id(Integer dormId);

	@Query(value = "SELECT u.role FROM DormitoryNotebooksJava.users  u WHERE u.id = :userID",nativeQuery = true)
	Optional<String> findUserRole(@Param("userID") Integer userID);

}
