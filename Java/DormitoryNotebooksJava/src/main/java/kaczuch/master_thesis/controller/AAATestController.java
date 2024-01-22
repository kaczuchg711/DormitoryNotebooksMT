package kaczuch.master_thesis.controller;

import jakarta.servlet.http.HttpServletRequest;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.repositories.UserRepository;
import kaczuch.master_thesis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @GetMapping("/add_user_to_organization")
    public String add_user_to_organization(Long userid, Long organizationId) {
        userOrganizationService.addUserToOrganization(userid, organizationId);
        return "redirect:/organization";
    }

    @GetMapping("/add_user_to_dorm")
    public String add_user_to_dorm(Long userid, Long dormID) {
        userDormServices.addUserToDorm(userid, dormID);
        return "redirect:/organization";
    }

    @GetMapping("/create_user")
    public String create_user(HttpServletRequest request) throws Exception {
        String rawPassword = "userPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User("c@c.com", encodedPassword, "USER", "Stefan", "Stefanowski");
        userRepository.save(user);

        Long dormID;
        if (dormService.getDormByName("Bydgoska").isPresent())
            dormID = dormService.getDormByName("Bydgoska").get().getId();
        else throw new Exception("Dorm not found ");
        add_user_to_dorm(user.getId(), dormService.getDormByName("Bydgoska").get().getId());

        Long organizationID;
        if (organizationService.findByAcronym("PK").isPresent())
            organizationID = organizationService.findByAcronym("PK").get().getId();
        else throw new Exception("Organization not found");
        add_user_to_organization(user.getId(), organizationID);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/organizations");
    }


}
