package com.pure.service.security;

import com.pure.service.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for Spring Security.
 */

@Component
public final class SecurityUtils {

    @Autowired
    private AuthorityRepository authorityRepository;

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS));
        }
        return false;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
        }
        return false;
    }

    public static List<String> getCurrentUserAuthorities() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null) {

            return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        } else {
            return new ArrayList<>();
        }
    }


    public static boolean isCurrentUserHeadmasterOrAdmin() {

        return isCurrentUserInRole(AuthoritiesConstants.ADMIN) || isCurrentUserInRole(AuthoritiesConstants.HEADMASTER);
    }
    public static boolean isCurrentUserHeadmasterOrAdminOrSalesManager() {

        return isCurrentUserInRole(AuthoritiesConstants.ADMIN) || isCurrentUserInRole(AuthoritiesConstants.HEADMASTER) || isCurrentUserInRole(AuthoritiesConstants.SALESMANAGER);
    }
}
