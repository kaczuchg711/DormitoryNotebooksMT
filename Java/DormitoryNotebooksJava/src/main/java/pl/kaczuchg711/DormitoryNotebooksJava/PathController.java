package pl.kaczuchg711.DormitoryNotebooksJava;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kaczuchg711.DormitoryNotebooksJava.organizations.OrganizationController;

@Controller
public class PathController {
    @GetMapping("/organizations")
    public String organizations(Model model) {
        OrganizationController controller = new OrganizationController();
        String respond = controller.handle_url_request(model);
        return respond;
    }
}