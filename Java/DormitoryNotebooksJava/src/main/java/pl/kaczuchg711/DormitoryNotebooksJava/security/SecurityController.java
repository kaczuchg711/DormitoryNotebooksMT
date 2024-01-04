package pl.kaczuchg711.DormitoryNotebooksJava.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @GetMapping("/")
    @ResponseBody // Add this if you want to return a text response directly
    public String handleOrganizationsUrlRequest(HttpSession session) {
        Integer organizationId = (Integer) session.getAttribute("organization_id");
        if (organizationId == null) {
            // Redirect to main page or show a message if organization is not set
            return "No organization selected. Redirecting to main page...";
        }
        return "Logged in with organization ID: " + organizationId;
    }
}