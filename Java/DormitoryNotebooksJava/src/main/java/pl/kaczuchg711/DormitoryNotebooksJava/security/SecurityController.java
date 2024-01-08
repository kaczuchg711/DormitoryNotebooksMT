package pl.kaczuchg711.DormitoryNotebooksJava.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.kaczuchg711.DormitoryNotebooksJava.organizations.Organization;
import pl.kaczuchg711.DormitoryNotebooksJava.organizations.OrganizationRepository;

import java.util.Optional;

@Controller
public class SecurityController {
    @Autowired
    private OrganizationRepository organizationRepository;
    @GetMapping("/")
    public ModelAndView handleStartPageUrlRequest(HttpSession session, Model model) {
        return processOrganizationCheck(session, model);
    }

    @GetMapping("/login")
    public ModelAndView fun(HttpSession session, Model model) {
        return processOrganizationCheck(session, model);
    }

    private ModelAndView processOrganizationCheck(HttpSession session, Model model) {
        Integer organizationId = (Integer) session.getAttribute("organization_id");

        if (organizationId == null) {
            return new ModelAndView("redirect:/organizations");
        }
        Optional<Organization> organizationOptional = organizationRepository.findById(organizationId);
        if (organizationOptional.isPresent()) {
            Organization organization = organizationOptional.get();
            model.addAttribute("organization_id", organization.getId());
            model.addAttribute("organization_acronym", organization.getAcronym());
            return new ModelAndView("login");
        } else {
            return new ModelAndView("redirect:/organizations");
        }
    }
}