package com.sauvignon.epidemicstatistics.controller;

import com.google.gson.Gson;
import com.sauvignon.epidemicstatistics.pojo.AreaData;
import com.sauvignon.epidemicstatistics.pojo.EchartsMapData;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.pojo.StatisticData;
import com.sauvignon.epidemicstatistics.service.DataService;
import com.sauvignon.epidemicstatistics.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphController
{
    @Autowired
    private GraphService graphService;
    @Autowired
    private DataService dataService;

    @RequestMapping("/lineGraph")
    public String lineGraph(Model model)
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
        return "lineGraph";
    }

    @RequestMapping("/mapGraph")
    public String mapGraph(Model model)
    {
        ArrayList<EchartsMapData> currentConfirmList=new ArrayList<>();
        ArrayList<EchartsMapData> confirmList=new ArrayList<>();
        List<SituationData> situationDataList=dataService.list4Show();
        for(SituationData sD:situationDataList)
        {
            currentConfirmList.add(new EchartsMapData(sD.getAreaName(),sD.getCurrentConfirm()));
            confirmList.add(new EchartsMapData(sD.getAreaName(),sD.getConfirm()));
        }
        Gson gson=new Gson();
        model.addAttribute("currentConfirmList",gson.toJson(currentConfirmList));
        model.addAttribute("confirmList",gson.toJson(confirmList));
        return "mapGraph";
    }

    @GetMapping("/importedCase")
    public String importedCase(Model model,Boolean sortByAsc)
    {
        model.addAttribute("barGraphElem","输入型病例");
        List<AreaData> importedCaseList=dataService.getAreaImportedCaseList(false);
        ArrayList<String> areaNameList=new ArrayList<>();
        ArrayList<Integer> confirmList=new ArrayList<>();
        ArrayList<Integer> currentConfirmList=new ArrayList<>();
        for(AreaData aD:importedCaseList)
        {
            areaNameList.add(aD.getAreaName());
            confirmList.add(aD.getConfirm());
            currentConfirmList.add(aD.getCurrentConfirm());
        }
        Gson gson=new Gson();
        model.addAttribute("areaNameList",gson.toJson(areaNameList));
        model.addAttribute("confirmList",gson.toJson(confirmList));
        model.addAttribute("currentConfirmList",gson.toJson(currentConfirmList));
        return "barGraph";
    }

}
