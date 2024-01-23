package kaczuch.master_thesis.controller;

import jakarta.servlet.http.HttpServletRequest;
import kaczuch.master_thesis.model.Breakdown;
import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.User;
import kaczuch.master_thesis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

import static kaczuch.master_thesis.controller.AAATestController.printRequestParameters;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class BreakdownsController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private BreakdownService breakdownService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;



    @Autowired
    private UserDormService userDormService;

    @GetMapping("/breakdowns")
    public ModelAndView giveBreakdownView() {
        Long currentUserId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail) {
                CustomUserDetail userDetails = (CustomUserDetail) principal;
                currentUserId = userDetails.getId();
            }
        }

        List<Long> userDormIds = userDormService.getDormIdsForUser(currentUserId);

        List<Breakdown> breakdownList = breakdownService.findAll();
        List<Breakdown> filteredBreakdownList = breakdownList.stream()
                .filter(breakdown -> userDormIds.contains(breakdown.getDorm().getId()))
                .collect(Collectors.toList());

        List<Map<String, Object>> breakdownData = filteredBreakdownList.stream().map(breakdown -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", breakdown.getId());
            map.put("description", breakdown.getDescription());
            map.put("isSolved", breakdown.getSolved());
            map.put("requestDate", breakdown.getRequestDate());
            map.put("dormId", breakdown.getDorm().getId());
            map.put("userId", breakdown.getUser().getId());

            Optional<User> optionalUser = customUserDetailsService.findById(breakdown.getUser().getId());
            if (optionalUser.isPresent()) {
                User user_reported_breakdown = optionalUser.get();
                map.put("firstName", user_reported_breakdown.getFirstName());
                map.put("lastName", user_reported_breakdown.getLastName());
                map.put("roomNumber", user_reported_breakdown.getRoomNumber());
            } else {
                map.put("firstName", "Not available");
                map.put("lastName", "Not available");
                map.put("roomNumber", "Not available");
            }
            return map;
        }).collect(Collectors.toList());

        String loggedUserRole = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                CustomUserDetail userDetails = (CustomUserDetail) principal;
                loggedUserRole = userDetails.getRole();
            }
        }
        boolean isPorter = Objects.equals(loggedUserRole, "PORTER");

        ModelAndView modelAndView = new ModelAndView("breakdowns");
        modelAndView.addObject("isPorter", isPorter);
        modelAndView.addObject("data", breakdownData);

        return modelAndView;
    }

    @PostMapping("/remove_breakdown")
    public ModelAndView removeBreakdown(HttpServletRequest request) {
        Long brakeDownIdToRemove = Long.valueOf(request.getParameter("breakdownId"));
        breakdownService.deleteById(brakeDownIdToRemove);
        return new ModelAndView("redirect:/breakdowns");

    }

    @PostMapping("/request_breakdown")
    public ModelAndView requestBreakdown(HttpServletRequest request) throws Exception {
        String description = request.getParameter("description");
        CustomUserDetail userDetails;
        Long currentUserId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetail) {
                userDetails = (CustomUserDetail) principal;
                currentUserId = userDetails.getId();
                //todo get dorm where user is logged not is assigned
                List<Dorm> userDorms = userDormService.getDormsForUser(currentUserId);

                Dorm userDorm = userDorms.get(0);

                customUserDetailsService.findById(currentUserId);

                Optional<User> userOpt = customUserDetailsService.findById(currentUserId);
                User user;
                if (userOpt.isPresent()) {
                    user = userOpt.get();
                } else throw new Exception("User nor found");

                Breakdown breakdown = new Breakdown(description, false, userDorm, user, new Date());

                breakdownService.save(breakdown);

            } else throw new Exception("Improper user in request_breakdown");
        }

        printRequestParameters(request);
        return new ModelAndView("redirect:/breakdowns");
    }
}
