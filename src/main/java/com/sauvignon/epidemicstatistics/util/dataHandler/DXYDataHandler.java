package com.sauvignon.epidemicstatistics.util.dataHandler;

import com.google.gson.Gson;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DXYDataHandler
{
    public static List getProvinceList(String htmlStr)
    {
        Document document= Jsoup.parse(htmlStr);
        Element oneScript=document.getElementById("getAreaStat");
        String data=oneScript.data();
        data=data.substring(data.indexOf("["),data.lastIndexOf("]")+1);
        Gson gson=new Gson();
        return gson.fromJson(data,List.class);
    }
    private static String getAreaName(Map areaInfoMap)
    {
        String areaName= (String) areaInfoMap.get("provinceName");
        if(areaName==null || "".equals(areaName))
            areaName= (String) areaInfoMap.get("cityName");
        return areaName;
    }
    public static List<SituationData> parseSituData(List areaList)
    {
        if(areaList==null || areaList.size()==0) return null;
        List<SituationData> result=new ArrayList<>();
        for(int i=0;i<areaList.size();i++)
        {
            Map areaInfoMap= (Map) areaList.get(i);
            String areaName=getAreaName(areaInfoMap);
            if(areaName==null || "".equals(areaName)) return null;
            SituationData oneData=new SituationData(null,
                    areaName,
                    null,
                    ((Double) areaInfoMap.get("confirmedCount")).intValue(),
                    ((Double) areaInfoMap.get("currentConfirmedCount")).intValue(),
                    ((Double) areaInfoMap.get("suspectedCount")).intValue(),
                    ((Double) areaInfoMap.get("deadCount")).intValue(),
                    null,
                    ((Double) areaInfoMap.get("curedCount")).intValue(),
                    null,
                    parseSituData((List) areaInfoMap.get("cities")));
            result.add(oneData);
        }
        return result;
    }
    public static List<SituationData> saveSituDataList(List areaList, String pName)
    {
        if(areaList==null) return null;
        List<SituationData> result=new ArrayList<>();
        for(int i=0;i<areaList.size();i++)
        {
            Map areaInfoMap= (Map) areaList.get(i);
            String areaName=getAreaName(areaInfoMap);
            SituationData oneData=new SituationData(null,
                    areaName,
                    pName,
                    ((Double) areaInfoMap.get("confirmedCount")).intValue(),
                    ((Double) areaInfoMap.get("currentConfirmedCount")).intValue(),
                    ((Double) areaInfoMap.get("suspectedCount")).intValue(),
                    ((Double) areaInfoMap.get("deadCount")).intValue(),
                    null,
                    ((Double) areaInfoMap.get("curedCount")).intValue(),
                    null,
                    null);
            result.add(oneData);
            List childrenList= (List) areaInfoMap.get("cities");
            if(childrenList!=null && childrenList.size()!=0)
                result.addAll(saveSituDataList(childrenList,areaName));
        }
        return result;
    }

}
