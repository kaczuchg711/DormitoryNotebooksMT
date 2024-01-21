package kaczuch.master_thesis.controller;

import kaczuch.master_thesis.model.Breakdown;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.service.BreakdownService;
import kaczuch.master_thesis.service.CustomUserDetail;
import kaczuch.master_thesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BreakdownsController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private BreakdownService breakdownService;

    @Autowired
    private UserService userService;

    @PostMapping("/breakdowns")
    public ModelAndView giveBreakdownView()
    {
        // Assuming you have a service that fetches data from the database
        List<Breakdown> breakdownList = breakdownService.findAll(); // Example method call to a service

        // Convert breakdownList to a format that can be easily used in the view
        List<Map<String, Object>> breakdownData = breakdownList.stream().map(breakdown -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", breakdown.getId());
            map.put("description", breakdown.getDescription());
            map.put("isSolved", breakdown.getSolved());
            map.put("requestDate", breakdown.getRequestDate());
            map.put("dormId", breakdown.getDorm().getId());
            map.put("userId", breakdown.getUser().getId());


            Optional<User> optionalUser = userService.findById(breakdown.getUser().getId());


            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                map.put("firstName", user.getFirstName());
                map.put("lastName", user.getLastName());
                map.put("roomNumber", user.getRoomNumber());
            }
            else
            {
                map.put("firstName", "Not available");
                map.put("lastName", "Not available");
                map.put("roomNumber", "Not available");
            }
            return map;
        }).collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("breakdowns");
        modelAndView.addObject("data", breakdownData);

        return modelAndView;
    }

}
