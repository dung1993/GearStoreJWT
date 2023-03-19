package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {

    @RequestMapping("")
    public String showHomePage(Model model){
        String title = "Key Board B1";
        model.addAttribute(title, title);

        return "home";
    }

    @RequestMapping("/temp")
    public String showTempPage(){
        return "temp";
    }
}
