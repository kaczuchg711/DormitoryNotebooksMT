package kaczuch.master_thesis.repositories;

import kaczuch.master_thesis.model.Rental;
import kaczuch.master_thesis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query(value = "SELECT r.* FROM rental r " +
            "INNER JOIN user_dorm ud ON r.user_id = ud.user_id " +
            "INNER JOIN item_to_rent it ON r.item_id = it.id " +
            "WHERE ud.dorm_id = :dormId AND it.name = :itemName", nativeQuery = true)
    List<Rental> findAllRentInDorm(@Param("dormId") Long dormId, @Param("itemName") String itemName);

    @Query(value = "SELECT r.* FROM rental r " +
            "INNER JOIN user_dorm ud ON r.user_id = ud.user_id " +
            "INNER JOIN item_to_rent it ON r.item_id = it.id " +
            "WHERE r.user_id = :userId AND it.name = :itemNameToRent AND it.is_available = false AND r.return_hour is null " , nativeQuery = true)
    List<Rental> findAllRentConcreteItemRentByUser(@Param("userId") Long userId, @Param("itemNameToRent") String itemNameToRent);
}