package kaczuch.master_thesis.service;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import kaczuch.master_thesis.model.Dorm;
import kaczuch.master_thesis.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private DormService dormService;

    @Autowired
    private OrganizationService organizationService;


    @Autowired
    UserDormService userDormService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String dormName = request.getParameter("dorm");
        Optional<Dorm> dorm = dormService.getDormByName(dormName);
        Integer dormID;
        Integer userID;
        if (dorm.isPresent() && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
            dormID = dorm.get().getId();
            userID = user.getId();
            if (!userDormService.isUserAssignedToDorm(userID, dormID)) {
                response.sendRedirect("/login_page?errorDorm=User is not assigned to this dorm");
                return;
            }
        } else {
            response.sendRedirect("/login_page?error=dormNotFound");
            return;
        }
        Optional<Organization> optionalOrganization = organizationService.findByAcronym(request.getParameter("organization_acronym"));

        if(!optionalOrganization.isPresent()){
            response.sendRedirect("/login_page?errorOrganization=User is not assigned to this organization");
            return;
        }

        Integer organizationID = optionalOrganization.get().getId();
        HttpSession session = request.getSession();
        session.setAttribute("organizationID", organizationID);
        session.setAttribute("dormID", dormID);
        session.setAttribute("userID", userID);

        redirect_depend_on_role(response, authentication);
    }

    private static void redirect_depend_on_role(HttpServletResponse response, Authentication authentication) throws IOException {
        var authourities = authentication.getAuthorities();
        var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();
        if (roles.orElse("").equals("ADMIN")) {
            response.sendRedirect("/admin-page");
        } else if (roles.orElse("").equals("USER") || (roles.orElse("").equals("PORTER"))) {
            response.sendRedirect("/user_dashboard");
        } else {
            response.sendRedirect("/login_page?error=wrong_redirect_depend_on_role");
        }
    }

}
