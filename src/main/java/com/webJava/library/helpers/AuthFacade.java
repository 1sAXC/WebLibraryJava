package com.webJava.library.helpers;
import org.springframework.security.core.Authentication;

public interface AuthFacade {
    Authentication getAuth();
    void setAuth(Authentication auth);
}
