package com.kaisikk.java.springboot.javaspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FirstController {
    @GetMapping("/firstcontroller")
    public String firstController(@RequestParam(name = "name", required = false, defaultValue = "Kaisik") String name, Model model){

    model.addAttribute("name", name);

    return "firsttemplate";
    }

}
