package kaczuch.master_thesis.service;

import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.ItemToRent;
import kaczuch.master_thesis.repositories.ItemToRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemToRentService {

    @Autowired
    private ItemToRentRepository itemToRentRepository;

    public ItemToRent createItemToRent(String name, Dorm dorm) {
        Integer highestNumber = itemToRentRepository.findHighestNumberByItemNameAndDorm(name, dorm);
        if (highestNumber == null) {
            highestNumber = 0;
        }
        Integer newNumber = highestNumber + 1;

        ItemToRent newItem = new ItemToRent();
        newItem.setName(name);
        newItem.setDorm(dorm);
        newItem.setNumber(newNumber);
        newItem.setAvailable(true);

        return itemToRentRepository.save(newItem);
    }

    public List<String> findDistinctNamesByDormId(Long dormId)
    {
        return itemToRentRepository.findDistinctNamesByDormId(dormId);
    }

    public List<ItemToRent> findAll() {
        return itemToRentRepository.findAll();
    }

}
