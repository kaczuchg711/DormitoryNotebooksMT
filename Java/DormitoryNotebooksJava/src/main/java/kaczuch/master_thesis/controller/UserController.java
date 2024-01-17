package kaczuch.master_thesis.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.Organization;
import kaczuch.master_thesis.repositories.OrganizationRepository;
import jakarta.servlet.http.HttpSession;
import kaczuch.master_thesis.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kaczuch.master_thesis.dto.UserDto;
import kaczuch.master_thesis.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationRepository organizationRepository;
	@Autowired
	private OrganizationService organizationService;


	@GetMapping("/registration")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		return "register";
	}
	
	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
		userService.save(userDto);
		model.addAttribute("message", "Registered Successfuly!");
		return "register";
	}

	@GetMapping({"/login", "/"})
	public String login(Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String organization_acronym = (String) session.getAttribute("organization_acronym");
		if (organization_acronym == null) {
			// Add a flash attribute to indicate the redirection
			redirectAttributes.addFlashAttribute("redirect", true);
			return "redirect:/organizations"; // Redirect to organizations page
		}


		model.addAttribute("organization_acronym", organization_acronym);

		Optional<Organization> organization = organizationService.findByAcronym(organization_acronym);
		if (organization.isPresent()) {
			Set<Dorm> dorms = organization.get().getDorms();
			List<String> dormNames = dorms.stream()
					.map(Dorm::getName)
					.collect(Collectors.toList());

			model.addAttribute("dormNames", dormNames); // Add dorm names to the model
		} else {
			// Handle the case where the organization is not found
			redirectAttributes.addFlashAttribute("error", "Organization not found.");
			return "redirect:/organizations";
		}

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String dormName = request.getParameter("dorms");

		return "login";
	}
	@GetMapping("/organizations")  // Map this method to handle requests to /organizations
	public String handleOrganizationsUrlRequest(Model model) {
		List<Organization> organizations = organizationRepository.findAll();
		model.addAttribute("organizations", organizations);
		return "organizations";
	}

	@PostMapping("/set_organization")  // Map this method to handle requests to /organizations
	public ModelAndView set_organization(@RequestParam("organization") String organizationAcronym, HttpSession session) {
		Optional<Organization> organization = organizationRepository.findByAcronym(organizationAcronym);
		if (organization.isPresent()) {
			session.setAttribute("organization_id", organization.get().getId());
			session.setAttribute("organization_acronym", organizationAcronym);
			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("redirect:/organization");
		}
	}
	
	@GetMapping("user-page")
	public String userPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "user";
	}
	
	@GetMapping("admin-page")
	public String adminPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "admin";
	}

}
