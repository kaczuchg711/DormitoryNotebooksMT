package pl.kaczuchg711.DormitoryNotebooksJava;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/showData")
    public String showData(Model model) {
        model.addAttribute("message", "Hello Thymeleaf!");
        return "MyFirstTemplate.html"; // name of your Thymeleaf template
    }
}