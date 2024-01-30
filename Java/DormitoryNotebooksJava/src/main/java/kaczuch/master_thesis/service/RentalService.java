package kaczuch.master_thesis.service;

import kaczuch.master_thesis.model.Rental;
import kaczuch.master_thesis.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> findAllRentInDorm(Integer dormID, String itemNameToRent)
    {
        return rentalRepository.findAllRentInDorm(dormID, itemNameToRent);
    }

    public List<Rental> findAllRentConcreteItemRentByUser(Integer userId, String itemNameToRent)
    {
        return rentalRepository.findAllRentConcreteItemRentByUser(userId, itemNameToRent);
    }

}