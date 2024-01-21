package kaczuch.master_thesis.controller;

import kaczuch.master_thesis.service.UserDormService;
import kaczuch.master_thesis.service.UserOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AAATestController {

    @Autowired
    UserOrganizationService userOrganizationService;

    @Autowired
    UserDormService userDormServices;

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
}
