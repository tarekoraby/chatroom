package com.chatroom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ChatController {

	@GetMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if(oldSession == null)
            return new ModelAndView( new RedirectView("/login"));
        else{
            Object username = oldSession.getAttribute("username");
            ModelAndView chatMaV = new ModelAndView("chat");
            chatMaV.addObject("username", username);
            String requestScheme = request.getScheme() == "http" ? "ws":"wss";
            String chatServerURL = requestScheme+"://"+request.getHeader("host")+"/chat";
            chatMaV.addObject("webSocketUrl", chatServerURL);
            return chatMaV;
        }
    }
	
}
