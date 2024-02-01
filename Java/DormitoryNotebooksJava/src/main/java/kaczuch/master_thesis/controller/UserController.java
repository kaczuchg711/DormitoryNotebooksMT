package kaczuch.master_thesis.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kaczuch.master_thesis.AuxiliaryFuns;
import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.Organization;
import kaczuch.master_thesis.repositories.OrganizationRepository;
import jakarta.servlet.http.HttpSession;
import kaczuch.master_thesis.service.*;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kaczuch.master_thesis.dto.UserDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ItemToRentService itemToRentService;
    @Autowired
    private UserDormService userDormService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
        userService.save(userDto);
        model.addAttribute("message", "Registered Successfuly!");
        return "register";
    }

    @GetMapping({"/login_page", "/"})
    public String login_page(@RequestParam(name = "errorDorm", required = false) String errorDorm, Model model,
                             HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Optional<Organization> organization = check_organization_choice(model, session, redirectAttributes);
        if (organization == null)
            return "redirect:/organizations";

        get_dorms_assigned_to_organization(model, organization);

        if (errorDorm != null) {
            model.addAttribute("errorDorm", errorDorm);
        }
        return "login_page";
    }

    @PostMapping("/login")
    public String login(Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return "user_dashboard";
    }


    private static void get_dorms_assigned_to_organization(Model model, Optional<Organization> organization) {
        Set<Dorm> dorms = organization.get().getDorms();
        List<String> dormNames = dorms.stream()
                .map(Dorm::getName)
                .collect(Collectors.toList());

        model.addAttribute("dormNames", dormNames);
    }

    private Optional<Organization> check_organization_choice(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        String organization_acronym = (String) session.getAttribute("organization_acronym");
        if (organization_acronym == null) {
            redirectAttributes.addFlashAttribute("redirect", true);
            return null;
        }
        model.addAttribute("organization_acronym", organization_acronym);
        Optional<Organization> organization = organizationService.findByAcronym(organization_acronym);
        if (!organization.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Organization not found.");
            return null;
        }
        return organization;
    }

    @GetMapping("/organizations")
    public String handleOrganizationsUrlRequest(Model model, ModelAndView modelAndView) {
        modelAndView.addObject("stefan", "Jestem stefan");
        List<Organization> organizations = organizationRepository.findAll();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }

    @PostMapping("/set_organization")
    public ModelAndView set_organization(@RequestParam("organization") String organizationAcronym, HttpSession session) {
        Optional<Organization> organization =
                organizationRepository.findByAcronym(organizationAcronym);
        if (organization.isPresent()) {
            session.setAttribute("organization_id", organization.get().getId());
            session.setAttribute("organization_acronym", organizationAcronym);
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("redirect:/organization");
        }
    }

    @GetMapping("user_dashboard")
    public String userPage(Model model, HttpServletRequest request) throws Exception {
        CustomUserDetail userDetails;
        Integer currentUserId = (Integer) request.getSession().getAttribute("userID");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail) {
                userDetails = (CustomUserDetail) principal;
                // Get dorm where user is logged not is assigned
                List<Dorm> userDorms = userDormService.getDormsForUser(currentUserId);
                Dorm userDorm = userDorms.get(0);

                List<String> uniqueItemsNames = itemToRentService.findDistinctNamesByDormId(userDorm.getId());

                model.addAttribute("uniqueItemsNames", uniqueItemsNames);

                AuxiliaryFuns.showAllParametersInRequest(request);

                model.addAttribute("user", userDetails);

                return "user_dashboard";
            } else {
                throw new Exception("User details not found in authentication principal.");
            }
        } else {
            throw new Exception("User is not authenticated.");
        }
    }


    @GetMapping("admin-page")
    public String adminPage(Model model, Principal principal) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }


}
