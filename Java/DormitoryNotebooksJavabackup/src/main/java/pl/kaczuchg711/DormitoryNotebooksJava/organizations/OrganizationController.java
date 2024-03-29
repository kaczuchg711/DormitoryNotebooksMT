package pl.kaczuchg711.DormitoryNotebooksJava.organizations;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.kaczuchg711.DormitoryNotebooksJava.security.User;
import pl.kaczuchg711.DormitoryNotebooksJava.security.Role;

import pl.kaczuchg711.DormitoryNotebooksJava.security.UserRepository;
import pl.kaczuchg711.DormitoryNotebooksJava.security.WebSecurityConfig;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller  // Use @Controller for Thymeleaf templates
public class OrganizationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrganizationRepository organizationRepository;



    @GetMapping("/create_test")  // Map this method to handle requests to /organizations
    public ModelAndView fun(Model model) {
        User user = new User();
        user.setUsername("testuser");
        passwordEncoder = WebSecurityConfig.passwordEncoder();
        user.setPassword(passwordEncoder.encode("testpassword"));
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        userRepository.save(user);

        Collection<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setAuthorities(roles);

        return new ModelAndView("redirect:/login");
    }
    @GetMapping("/organizations")  // Map this method to handle requests to /organizations
    public String handleOrganizationsUrlRequest(Model model) {
        List<Organization> organizations = organizationRepository.findAll();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }



    @PostMapping("/set_organization") // Use @PostMapping for POST requests
    public ModelAndView setOrganization(@RequestParam("organization") String organizationAcronym, HttpSession session) {
        Optional<Organization> organization = organizationRepository.findByAcronym(organizationAcronym);
        if (organization.isPresent()) {
            session.setAttribute("organization_id", organization.get().getId());
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("redirect:/organization");
        }
    }

}