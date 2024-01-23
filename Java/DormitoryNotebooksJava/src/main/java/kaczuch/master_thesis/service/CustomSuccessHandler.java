package kaczuch.master_thesis.service;

import java.io.IOException;
import java.util.Optional;

import kaczuch.master_thesis.model.Dorm;
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
    UserDormService userDormService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String dormName = request.getParameter("dorm");

        Optional<Dorm> dorm = dormService.getDormByName(dormName);

//        modelAndView.addObject("dorm",dormName);

        if (dorm.isPresent() && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
            Long dormID = dorm.get().getId();

            if (!userDormService.isUserAssignedToDorm(user.getId(), dormID)) {
                response.sendRedirect("/login_page?errorDorm=User is not assigned to this dorm");
                return;
            }
        } else {
            System.out.println("        } else {");
            response.sendRedirect("/login_page?error=dormNotFound");
            return;
        }

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
