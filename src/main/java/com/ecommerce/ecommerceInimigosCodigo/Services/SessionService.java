package com.ecommerce.ecommerceInimigosCodigo.Services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {

    public static final String SESSION_USER_EMAIL = "userEmail";
    public static final String SESSION_USER_ROLE = "userRole";

    public void setUserInSession(HttpSession session, String email, String role) {
        session.setAttribute(SESSION_USER_EMAIL, email);
        session.setAttribute(SESSION_USER_ROLE, role);
    }

    public String getUserEmailFromSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_USER_EMAIL);
    }

    public String getUserRoleFromSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_USER_ROLE);
    }

    public void removeUserFromSession(HttpSession session) {
        session.removeAttribute(SESSION_USER_EMAIL);
        session.removeAttribute(SESSION_USER_ROLE);
    }
}
