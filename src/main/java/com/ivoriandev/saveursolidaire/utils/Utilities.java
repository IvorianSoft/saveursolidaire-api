package com.ivoriandev.saveursolidaire.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class Utilities {
    public static Date getCurrentDate(){return new Date();}

    public static String getAuthenticateUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && !(auth instanceof AnonymousAuthenticationToken)) ? auth.getName() : null;
    }
}
