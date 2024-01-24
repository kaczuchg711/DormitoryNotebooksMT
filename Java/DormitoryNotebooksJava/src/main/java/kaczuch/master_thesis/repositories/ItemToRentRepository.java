package kaczuch.master_thesis.repositories;

import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.ItemToRent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemToRentRepository extends JpaRepository<ItemToRent, Long> {
    @Query("SELECT MAX(i.number) FROM ItemToRent i WHERE i.name = :name AND i.dorm = :dorm")
    Integer findHighestNumberByItemNameAndDorm(String name, Dorm dorm);

    @Query("SELECT DISTINCT i.name FROM ItemToRent i WHERE i.dorm.id = :dormId")
    List<String> findDistinctNamesByDormId(@Param("dormId") Long dormId);

    List<ItemToRent> findByNameContainingAndDormId(String name, Long dormId);

    @Query(value = "SELECT it.* FROM item_to_rent it WHERE it.dorm_id = :dormId AND it.name = :itemName AND it.is_Available = true", nativeQuery = true)
    List<ItemToRent> findAvailableItemsByNameAndDormId(@Param("dormId") Long dormId, @Param("itemName") String itemName);
}
