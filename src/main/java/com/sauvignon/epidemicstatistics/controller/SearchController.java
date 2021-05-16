package com.sauvignon.epidemicstatistics.controller;

import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.service.ElaSearchService;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController
{
    @Autowired
    private ElaSearchService elaSearchService;

    @RequestMapping("/area")
    public String searchArea(String areaName, Model model)
    {
        SearchHits searchHits = elaSearchService.searchArea(areaName);
        if(searchHits==null) return null;

        SearchHit hits[]= searchHits.getHits();
        ArrayList<SituationData> dataList=new ArrayList<>();
        for(SearchHit document:hits)
        {
            SituationData oneData=new SituationData();
            Map fieldMap=document.getSourceAsMap();
            oneData.setAreaName((String) fieldMap.get("areaName"));
            oneData.setConfirm((Integer) fieldMap.get("confirm"));
            oneData.setCurrentConfirm((Integer) fieldMap.get("currentConfirm"));
            oneData.setDead((Integer) fieldMap.get("dead"));
            oneData.setDeadRate(((Double) fieldMap.get("deadRate")).floatValue());
            oneData.setHeal((Integer) fieldMap.get("heal"));
            oneData.setHealRate(((Double) fieldMap.get("healRate")).floatValue());
            oneData.setPName((String) fieldMap.get("pName"));
            oneData.setSuspect((Integer) fieldMap.get("suspect"));
            dataList.add(oneData);
        }
        model.addAttribute("dataList",dataList);
        return "dataList";
    }
}
