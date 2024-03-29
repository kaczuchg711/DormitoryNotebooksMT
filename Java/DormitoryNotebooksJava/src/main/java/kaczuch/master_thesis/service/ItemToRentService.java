package kaczuch.master_thesis.service;

import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.ItemToRent;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.repositories.ItemToRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<String> findDistinctNamesByDormId(Integer dormId)
    {
        return itemToRentRepository.findDistinctNamesByDormId(dormId);
    }

    public List<ItemToRent> findAll() {
        return itemToRentRepository.findAll();
    }

    public List<ItemToRent> getItemsByNameAndDormId (String filterName,Integer dormId)
    {
        return itemToRentRepository.findByNameContainingAndDormId(filterName, dormId);
    }

    public List<ItemToRent> getAvailableItemsInDormByName(Integer dormId, String itemName) {
        return itemToRentRepository.findAvailableItemsByNameAndDormId(dormId, itemName);
    }

    public Optional<ItemToRent> findById(Integer id)
    {
        return itemToRentRepository.findById(id);
    }

    public void save(ItemToRent item) {
    }
}
