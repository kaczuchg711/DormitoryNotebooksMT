package kaczuch.master_thesis.controller;

import ch.qos.logback.core.AppenderBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
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

import java.net.URLEncoder;
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
        HttpSession session = request.getSession();
        String itemNameToRent = request.getParameter("item");
        System.out.println("AAAAAAAAAA");
        System.out.println("AAAAAAAAAA");
        System.out.println("AAAAAAAAAA");
        System.out.println(itemNameToRent);
        System.out.println("AAAAAAAAAA");
        System.out.println("AAAAAAAAAA");
        Integer currentUserId = (Integer) session.getAttribute("userID");
        Integer dormId = (Integer) session.getAttribute("dormID");

        List<Rental> rentals = rentalService.findAllRentInDorm(dormId, itemNameToRent);
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

        Integer userID = (Integer) request.getSession().getAttribute("userID");
        Optional<User> userOPT = userService.findById(userID);
        User user;
        if (userOPT.isPresent())
            user = userOPT.get();
        else throw new Exception("User not found");


        CustomUserDetail userDetail = new CustomUserDetail(user);


        Optional<ItemToRent> optionalItem = itemService.findById(selectedItemId);

        ItemToRent item;

        if (optionalItem.isPresent()) item = optionalItem.get();
        else throw new Exception("item is not present");

        Rental rental = new Rental(userDetail.getUser(), item, LocalDate.now(), LocalTime.now(), null);
        item.setAvailable(false);
        itemToRentRepository.save(item);
        rentalRepository.save(rental);
        String parameterValue = item.getName();
        String encodedValue = URLEncoder.encode(parameterValue, "UTF-8");
        String redirect_url = "redirect:/rent_page?item=" + parameterValue;
        return new ModelAndView(redirect_url);
    }

    @PostMapping("/return_item")
    public ModelAndView returnItem(HttpServletRequest request, ModelAndView modelAndView) throws Exception {
        Integer userId = (Integer) request.getSession().getAttribute("userID");
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
