package kaczuch.master_thesis.controller;

import jakarta.servlet.http.HttpServletRequest;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.repositories.UserRepository;
import kaczuch.master_thesis.service.UserDormService;
import kaczuch.master_thesis.service.UserOrganizationService;
import kaczuch.master_thesis.service.UserService;
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
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/add_user_to_organization")
    public String add_user_to_organization()
    {
        userOrganizationService.addUserToOrganization(1L,1);
        return "redirect:/organization";
    }

    @GetMapping("/add_user_to_dorm")
    public String add_user_to_dorm()
    {
        userDormServices.addUserToDorm(1L,1);
        return "redirect:/organization";
    }

    @GetMapping("/create_user")
    public String create_user(HttpServletRequest request)
    {
        String rawPassword = "userPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User("b@b.com",encodedPassword,"USER","Jan","Nowak");
        userRepository.save(user);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/defaultPage");
    }
}
