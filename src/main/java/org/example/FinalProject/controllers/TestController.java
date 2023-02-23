package org.example.FinalProject.controllers;

import org.example.FinalProject.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String showTest(Model model){
        model.addAttribute("tests", testService.listTest());
        return "/test";
    }
}
