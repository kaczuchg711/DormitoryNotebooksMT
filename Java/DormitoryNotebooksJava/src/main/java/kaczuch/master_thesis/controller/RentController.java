package kaczuch.master_thesis.controller;

import ch.qos.logback.core.AppenderBase;
import jakarta.servlet.http.HttpServletRequest;
import kaczuch.master_thesis.model.*;
import kaczuch.master_thesis.repositories.ItemToRentRepository;
import kaczuch.master_thesis.repositories.RentalRepository;
import kaczuch.master_thesis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import kaczuch.master_thesis.model.RentalDetailsDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static kaczuch.master_thesis.controller.AAATestController.*;

@Controller
public class RentController {

    @Autowired
    ItemToRentService itemToRentService;

    @Autowired
    UserDormService userDormService;

    @Autowired
    ItemToRentService itemService;

    @Autowired
    RentalService rentalService;

    @Autowired
    UserService userService;

    @Autowired
    private ItemToRentRepository itemToRentRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping("/rent_page")
    public ModelAndView handleRentRequest(HttpServletRequest request) throws Exception {


        String itemNameToRent = request.getParameter("item");
        CustomUserDetail userDetails;
        Integer currentUserId;

        CustomUserDetail userDetail = getLoggedUser();
        currentUserId = userDetail.getId();


//      odo get dorm from user not from login

        List<Dorm> userDorms = userDormService.getDormsForUser(currentUserId);
        Dorm userDorm = userDorms.get(0);
        Integer dormId = userDorm.getId();

        List<Rental> rentals = rentalService.findAllRentInDorm(userDorm.getId(), itemNameToRent);
        List<RentalDetailsDTO> rentalDetails = rentals.stream().map(rental -> {
            User user = rental.getUser();
            RentalDetailsDTO dto = new RentalDetailsDTO();
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setRoomNumber(user.getRoomNumber());
            dto.setDate(rental.getRentalDate());
            dto.setTime(rental.getRentHour());
            return dto;
        }).toList();

        boolean userRentNowItem = rentals.stream()
                .anyMatch(rental -> rental.getUser().getId().equals(currentUserId) &&
                        rental.getReturnHour() == null);


        List<ItemToRent> availableItems = itemToRentService.getAvailableItemsInDormByName(dormId, itemNameToRent);
        ModelAndView modelAndView = new ModelAndView("rental_page");
        modelAndView.addObject("rentalDetails", rentalDetails);
        modelAndView.addObject("availableItems", availableItems);
        modelAndView.addObject("userRentNowItem", userRentNowItem);

        printRequestParameters(request);
        printAllObjectsInModelAndView(modelAndView);
        return modelAndView;
    }

    @PostMapping("/rent_item")
    public ModelAndView handleRent(HttpServletRequest request) throws Exception {
        AAATestController.printRequestParameters(request);


        Integer selectedItemId = Integer.valueOf(request.getParameter("selectedItem"));


        CustomUserDetail userDetail = getLoggedUser();


        Optional<ItemToRent> optionalItem = itemService.findById(selectedItemId);

        ItemToRent item;

        if (optionalItem.isPresent()) item = optionalItem.get();
        else throw new Exception("item is not present");

        Rental rental = new Rental(userDetail.getUser(), item, LocalDate.now(), LocalTime.now(), null);
        item.setAvailable(false);
        itemToRentRepository.save(item);
        rentalRepository.save(rental);


        return new ModelAndView("redirect:/rent_page?item=" + item.getName());
    }

    @PostMapping("/return_item")
    public ModelAndView returnItem(HttpServletRequest request, ModelAndView modelAndView) throws Exception {
        CustomUserDetail customUserDetail = getLoggedUser();
        Integer userId = customUserDetail.getId();
        String itemName = request.getParameter("item_name");

        if (rentalService.findAllRentConcreteItemRentByUser(userId, itemName).size() != 1)
            throw new Exception("findAllRentConcreteItemRentByUser return more that one object");
        Rental rental = rentalService.findAllRentConcreteItemRentByUser(userId, itemName).get(0);
        ItemToRent itemToRent = rental.getItem();

        rental.setReturnHour(LocalTime.now());
        itemToRent.setAvailable(true);
        System.out.println(itemToRent.getId());

        itemToRentRepository.save(itemToRent);
        rentalRepository.save(rental);

        return new ModelAndView("redirect:/rent_page?item=" + itemToRent.getName());
    }
}
