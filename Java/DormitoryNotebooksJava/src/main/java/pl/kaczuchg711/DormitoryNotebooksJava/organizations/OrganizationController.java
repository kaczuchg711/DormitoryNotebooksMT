package pl.kaczuchg711.DormitoryNotebooksJava.organizations;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller  // Use @Controller for Thymeleaf templates
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

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