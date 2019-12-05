package com.chatroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null){
            oldSession.invalidate();
        }
        return new ModelAndView(new RedirectView("/login"));
    }
}