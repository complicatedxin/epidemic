package com.sauvignon.epidemicstatistics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageIndexController
{
    @RequestMapping("/")
    public String index()
    {
        return "forward:/areaData";
    }

}
