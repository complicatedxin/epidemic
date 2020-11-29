package com.sauvignon.epidemicstatistics.controller;

import com.sauvignon.epidemicstatistics.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DataController
{
    @Autowired
    private DataService dataService;

    @GetMapping("/areaData")
    public String dataList(Model model)
    {
        model.addAttribute("dataList", dataService.list4Show());
        return "dataList";
    }

}
