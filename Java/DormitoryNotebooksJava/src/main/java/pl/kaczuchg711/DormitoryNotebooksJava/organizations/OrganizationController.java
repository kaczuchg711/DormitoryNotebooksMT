package pl.kaczuchg711.DormitoryNotebooksJava.organizations;

import org.springframework.ui.Model;

public class OrganizationController {
    public String handle_url_request(Model model) {
        model.addAttribute("organizations", "org 1 org 2 org 3");
        return "organizations.html";
    }
}
