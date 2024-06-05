package com.mmozhzhe.featuretoggle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SinglePageApplicationController {

    @RequestMapping(value = {"/", "/features/**", "/releases"})
    public String index() {
        return "index.html";
    }
}
