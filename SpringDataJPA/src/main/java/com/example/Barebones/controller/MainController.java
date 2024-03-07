package com.example.Barebones.controller;

import java.util.Collection;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class MainController {

    @GetMapping("/")
	public String root(HttpSession session) {

		System.out.println("IN  MainController->root()");
		session.getAttributeNames();
		
		Collection<? extends GrantedAuthority> userRoles;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userRoles = ((UserDetails) principal).getAuthorities();

			for (GrantedAuthority userRole : userRoles) {
				if (userRole.getAuthority().equals("ROLE_SUPER")) {
					System.out.println("USER ROLE="+userRole.getAuthority());
					return "redirect:/admin";
				}
			}
		}
		
		System.out.println("USER ROLE Defaults to Regular USER");
		return "redirect:/students";
	}
	
    @GetMapping("/admin")
    public String admin(Model model) {
    	System.out.println("IN  MainController->admin()");
        return "admin";
    }
    
    @GetMapping("/login")
    public String login(Model model) {
    	System.out.println("IN  MainController->login()");
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
    	System.out.println("IN  MainController->userIndex()");
        return "user/index";
    }

    @ResponseBody
	@GetMapping("/logoutSuccess")
    public String logoutResponse()
    {
    	System.out.println("IN  MainController->logoutResponse()");
        return "Logged Out!!!!";
    }

}
