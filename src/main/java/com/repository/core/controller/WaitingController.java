package com.repository.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/wait")
public class WaitingController {

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("common/waiting");
    }
}
