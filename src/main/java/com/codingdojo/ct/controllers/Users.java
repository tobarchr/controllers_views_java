package com.codingdojo.ct.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.ct.models.User;
import com.codingdojo.ct.services.UserService;

	@Controller
	public class Users {
		private final UserService userService;
	    
	    public Users(UserService userService) {
	        this.userService = userService;
	    }
	    
	    @RequestMapping("/registration")
	    public String registerForm(Model model) {
	    	model.addAttribute("user", new User());
	        return "registrationPage.jsp";
	    }
	    @RequestMapping("/login")
	    public String login() {
	        return "loginPage.jsp";
	    }
	    
	    @RequestMapping(value="/registration", method=RequestMethod.POST)
	    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
	    	if(result.hasErrors()) {
	    		return "registationPage.jsp";
	    	}
	    	User u = userService.registerUser(user);
	    	session.setAttribute("user_id", u.getId());
	    	return "redirect:/home";
	    }
	    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
			boolean isAuthenticated = userService.authenticateUser(email, password);
	    	if(isAuthenticated) {
	    		User u = userService.findByEmail(email);
	    		session.setAttribute("userId", u.getId());
	    		return "redirect:/home";
	    	}
	    	model.addAttribute("error","Wrong credentials, Please retry");
	    	return "loginPage.jsp";
	    }
	    
	    @RequestMapping("/home")
	    public String home(HttpSession session, Model model) {
	    Long userId = (Long) session.getAttribute("user_id");
		User u = userService.findUserById(userId);
	    model.addAttribute("user",u);
	    return "homePage.jsp";
	    
	    }
	    @RequestMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/login";
	    }
	        		
}


