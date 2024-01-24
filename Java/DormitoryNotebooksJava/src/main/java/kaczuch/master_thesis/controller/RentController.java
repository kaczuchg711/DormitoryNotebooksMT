package kaczuch.master_thesis.controller;

import jakarta.servlet.http.HttpServletRequest;
import kaczuch.master_thesis.model.*;
import kaczuch.master_thesis.service.CustomUserDetail;
import kaczuch.master_thesis.service.ItemToRentService;
import kaczuch.master_thesis.service.RentalService;
import kaczuch.master_thesis.service.UserDormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import kaczuch.master_thesis.model.RentalDetailsDTO;
import java.util.List;

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

    @GetMapping("/rent")
    public ModelAndView handleRentRequest(HttpServletRequest request) throws Exception {
        String itemNameToRent = request.getParameter("item");
        CustomUserDetail userDetails;
        Long currentUserId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail) {
                userDetails = (CustomUserDetail) principal;
                currentUserId = userDetails.getId();
//                todo get dorm from user not from login

                List<Dorm> userDorms = userDormService.getDormsForUser(currentUserId);
                Dorm userDorm = userDorms.get(0);
                Long dormId = userDorm.getId();

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

                List<ItemToRent> availableItems = itemToRentService.getAvailableItemsInDormByName(dormId, itemNameToRent);
                ModelAndView modelAndView = new ModelAndView("rental_page");
                modelAndView.addObject("rentalDetails", rentalDetails);
                modelAndView.addObject("availableItems", availableItems);
                System.out.println(availableItems);
                return modelAndView;

            }
        }
        else {
            throw new Exception("authentication db problem in public ModelAndView handleRentRequest(HttpServletRequest request)");
        }

        return null;
    }
}
