//package kaczuch.master_thesis.configurations;
//
//import kaczuch.master_thesis.model.User;
//import kaczuch.master_thesis.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        String dormName = request.getParameter("dormName");
//
//        // Retrieve the user based on the username
//        User user = userRepository.findByEmail(username);
//
//        // Check if the user is associated with the provided dorm
//        if (user != null && user.getDorms().stream().anyMatch(dorm -> dorm.getName().equals(dormName))) {
//            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//            setDetails(request, authRequest);
//            return this.getAuthenticationManager().authenticate(authRequest);
//        } else {
//            throw new BadCredentialsException("Invalid dorm information");
//        }
//    }
//}
//
//
