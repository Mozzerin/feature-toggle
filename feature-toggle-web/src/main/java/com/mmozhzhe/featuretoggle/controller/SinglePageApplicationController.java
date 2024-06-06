package com.mmozhzhe.featuretoggle.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SinglePageApplicationController {

    @RequestMapping(value = {"/", "/features", "/releases", "/features/feature/**"})
    public String index(HttpServletRequest request) {
        return "/index.html";
    }
}
