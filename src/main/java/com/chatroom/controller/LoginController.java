package com.chatroom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@PostMapping("/login")
	public ModelAndView loginSetUsername(String username, HttpServletRequest request) {
		if (username == null || username == "") {
			return new ModelAndView("login");
		} else {
			HttpSession oldSession = request.getSession(false);
			if (oldSession != null) {
				oldSession.invalidate();
			}
			HttpSession newSession = request.getSession();
			newSession.setAttribute("username", username);
			return new ModelAndView(new RedirectView("/"));
		}
	}

}
