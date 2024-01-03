package pl.kaczuchg711.DormitoryNotebooksJava.organizations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller  // Use @Controller for Thymeleaf templates
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping("/organizations")  // Map this method to handle requests to /organizations
    public String handleUrlRequest(Model model) {
        List<Organization> organizations = organizationRepository.findAll();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }


}