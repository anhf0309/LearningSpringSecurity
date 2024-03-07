package com.example.Barebones.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Barebones.model.User;
import com.example.Barebones.repo.UserRegistrationDto;
import com.example.Barebones.service.UserService;



@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

   @Autowired
   private UserService userService;

   @ModelAttribute("user")
   public UserRegistrationDto userRegistrationDto() {
	   System.out.println("IN  UserRegController->UserRegistrationDto()");
       return new UserRegistrationDto();
   }

   @GetMapping
   public String showRegistrationForm(Model model) {
	   System.out.println("IN  UserRegController->showRegistrationForm()");
       return "registration";
   }

   @PostMapping
   public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result){

	   System.out.println("IN  POST MAPPING UserRegController->registerUserAccount()");
       User existing = userService.findByEmail(userDto.getEmail());
       if (existing != null){
           result.rejectValue("email", null, "There is already an account registered with that email");
       }
       
       System.out.println("ZZZZZZZZZZZZZZZ result:"+result.toString());
       
       if (result.hasErrors()){
    	   System.out.println("result:"+result.toString());
           return "registration";
       }
       

       userService.save(userDto);
       return "redirect:/registration?success";
   }
}