package kaczuch.master_thesis.controller;

import jakarta.servlet.http.HttpServletRequest;
import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.Organization;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.repositories.UserRepository;
import kaczuch.master_thesis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;

@Controller
public class AAATestController {

    @Autowired
    UserOrganizationService userOrganizationService;

    @Autowired
    UserDormService userDormServices;

    @Autowired
    DormService dormService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ItemToRentService itemToRentService;

    @GetMapping("/add_item_to_rent")
    public String addItemToRent(HttpServletRequest request) throws Exception {
        Optional<Dorm> dormOpt = dormService.getDormByName("Bydgoska");
        Dorm dorm;
        if (dormOpt.isPresent()) {
            dorm = dormOpt.get();
            itemToRentService.createItemToRent("Odkurzacz", dorm);
            itemToRentService.createItemToRent("Odkurzacz", dorm);
            itemToRentService.createItemToRent("Odkurzacz", dorm);
            itemToRentService.createItemToRent("Miotła", dorm);
            itemToRentService.createItemToRent("Pralka", dorm);
            itemToRentService.createItemToRent("Pralka", dorm);
            String referer = request.getHeader("Referer");
            return "redirect:" + (referer != null ? referer : "/organizations");
        } else {
            throw new Exception("dorm not found");
        }
    }

    @GetMapping("/add_user_to_organization")
    public String add_user_to_organization(Integer userid, Integer organizationId) {
        userOrganizationService.addUserToOrganization(userid, organizationId);
        return "redirect:/organization";
    }

    @GetMapping("/add_user_to_dorm")
    public String add_user_to_dorm(Integer userid, Integer dormID) {
        userDormServices.addUserToDorm(userid, dormID);
        return "redirect:/organization";
    }

    @GetMapping("/create_user")
    public String create_user(HttpServletRequest request) throws Exception {
        String rawPassword = "userPassword";
        String mail = "b@b.com";
        String role = "PORTER";
        String userName = "Pan Janusz";
        String userLastName = "Januszowski";
        String pk = "PK";
        String dorm = "Bydgoska";

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(mail, encodedPassword, role, userName, userLastName);

        userRepository.save(user);
        Integer dormID;
        if (dormService.getDormByName(dorm).isPresent())
            dormID = dormService.getDormByName(dorm).get().getId();
        else throw new Exception("Dorm not found ");
        add_user_to_dorm(user.getId(), dormID);

        Integer organizationID;
        Optional<Organization> optionalOrganization = organizationService.findByAcronym(pk);
        if (optionalOrganization.isPresent())
            organizationID = optionalOrganization.get().getId();
        else throw new Exception("Organization not found");
        add_user_to_organization(user.getId(), organizationID);
        userRepository.save(user);
        String referer = request.getHeader("Referer");

        System.out.println("User created successful");
        System.out.println("User created successful");
        System.out.println("User created successful");
        System.out.println("User created successful");

        return "redirect:" + (referer != null ? referer : "/organizations");
    }

    public static void printAllObjectsInModelAndView(ModelAndView modelAndView) {
        Map<String, Object> modelMap = modelAndView.getModel();
        for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    public static void printRequestParameters(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println(paramName + ": " + paramValue);
        }
    }
}
