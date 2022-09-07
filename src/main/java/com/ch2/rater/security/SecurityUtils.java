package com.ch2.rater.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@UtilityClass
public class SecurityUtils {
    public static String getCurrentUserId() {
        return (String) (
            (Jwt) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
        )
            .getClaims()
            .get("sub");
    }
}
