//package kaczuch.master_thesis.configurations;
//
//
//import kaczuch.master_thesis.service.UserDormService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//    @Autowired
//    private UserDormService userDormService; // Service to check dorm assignments
//
////    @Override
////    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
////        String username = authentication.getName();
//////        String dormName = ... // Retrieve the dorm name from the authentication request
////
////        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//////        if (!userDormService.isUserAssignedToDorm(userDetails.getUsername(), dormName)) {
//////            throw new BadCredentialsException("Access Denied for this dormitory.");
//////        }
////
////        // Continue with the regular authentication process
////
////    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
