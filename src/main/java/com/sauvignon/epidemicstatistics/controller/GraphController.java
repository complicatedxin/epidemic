package com.sauvignon.epidemicstatistics.controller;

import com.google.gson.Gson;
import com.sauvignon.epidemicstatistics.pojo.StatisticData;
import com.sauvignon.epidemicstatistics.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphController
{
    @Autowired
    private GraphService graphService;

    @RequestMapping("/graphData")
    public String graphData(Model model)
    {
        List<String> dateList=new ArrayList<>();
        List<Integer> confirmList=new ArrayList<>();
        List<Integer> currentConfirmList=new ArrayList<>();
        List<Integer> currentSevereList=new ArrayList<>();
        List<Integer> importedCaseList=new ArrayList<>();
        List<Integer> suspectList=new ArrayList<>();
        List<Integer> deadList=new ArrayList<>();
        List<Integer> healList=new ArrayList<>();
        List<StatisticData> crudeList=graphService.captureList(false);
        for(StatisticData sd:crudeList)
        {
            dateList.add(sd.getSdDate());
            confirmList.add(sd.getConfirm());
            currentConfirmList.add(sd.getCurrentConfirm());
            currentSevereList.add(sd.getCurrentSevere());
            importedCaseList.add(sd.getImportedCase());
            suspectList.add(sd.getSuspect());
            deadList.add(sd.getDead());
            healList.add(sd.getHeal());
        }
        Gson gson=new Gson();
        model.addAttribute("dateList",gson.toJson(dateList));
        model.addAttribute("confirmList",gson.toJson(confirmList));
        model.addAttribute("currentConfirmList",gson.toJson(currentConfirmList));
        model.addAttribute("currentSevereList",gson.toJson(currentSevereList));
        model.addAttribute("importedCaseList",gson.toJson(importedCaseList));
        model.addAttribute("suspectList",gson.toJson(suspectList));
        model.addAttribute("deadList",gson.toJson(deadList));
        model.addAttribute("healList",gson.toJson(healList));
        return "graph";
    }
}
