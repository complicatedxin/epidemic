package com.sauvignon.epidemicstatistics.util;

import com.google.gson.Gson;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.pojo.StatisticData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SauUtil
{
    public static List<SituationData> parseJson4SituData(String jsonStr, boolean save)
    {
        List<SituationData> dataList=null;
        Gson gson=new Gson();
        Map jsonMap=gson.fromJson(jsonStr, Map.class);
        jsonStr= (String) jsonMap.get("data");
        Map infoMap=gson.fromJson(jsonStr, Map.class);
        Map chinaTotalMap= (Map) infoMap.get("chinaTotal");
        Map chinaAddMap= (Map) infoMap.get("chinaAdd");
        List areaTreeList= (List) infoMap.get("areaTree");
        Map areaTreeMap= (Map) areaTreeList.get(0);

        List provinceList= (List) areaTreeMap.get("children");
        if(save)
            return getTXDataList(provinceList,null);
        dataList=parseJsonFromTX(provinceList);
        return dataList;
    }
    private static List<SituationData> parseJsonFromTX(List areaList)
    {
        if(areaList==null || areaList.size()==0) return null;
        List<SituationData> result=new ArrayList<>();
        for(int i=0;i<areaList.size();i++)
        {
            Map areaInfoMap= (Map) areaList.get(i);
            String areaName= (String) areaInfoMap.get("name");
            Map todayMap= (Map) areaInfoMap.get("today");
            Map totalMap= (Map) areaInfoMap.get("total");
            SituationData oneData=new SituationData(null,
                    areaName,
                    null,
                    ((Double) totalMap.get("confirm")).intValue(),
                    ((Double) totalMap.get("nowConfirm")).intValue(),
                    ((Double) totalMap.get("suspect")).intValue(),
                    ((Double) totalMap.get("dead")).intValue(),
                    Float.parseFloat((String) totalMap.get("deadRate")),
                    ((Double) totalMap.get("heal")).intValue(),
                    Float.parseFloat((String) totalMap.get("healRate")),
                    parseJsonFromTX((List) areaInfoMap.get("children")));
            result.add(oneData);
        }
        return result;
    }
    private static List<SituationData> getTXDataList(List areaList, String pName)
    {
        if(areaList==null) return null;
        List<SituationData> result=new ArrayList<>();
        for(int i=0;i<areaList.size();i++)
        {
            Map areaInfoMap= (Map) areaList.get(i);
            String areaName= (String) areaInfoMap.get("name");
            Map totalMap= (Map) areaInfoMap.get("total");
            SituationData oneData=new SituationData(null,
                    areaName,
                    pName,
                    ((Double) totalMap.get("confirm")).intValue(),
                    ((Double) totalMap.get("nowConfirm")).intValue(),
                    ((Double) totalMap.get("suspect")).intValue(),
                    ((Double) totalMap.get("dead")).intValue(),
                    Float.parseFloat((String) totalMap.get("deadRate")),
                    ((Double) totalMap.get("heal")).intValue(),
                    Float.parseFloat((String) totalMap.get("healRate")),
                    null);
            result.add(oneData);
            List childrenList= (List) areaInfoMap.get("children");
            if(childrenList!=null && childrenList.size()!=0)
                result.addAll(getTXDataList(childrenList,areaName));
        }
        return result;
    }

    public static List<SituationData> parseHTML4SituData(String htmlStr, boolean save)
    {
        List<SituationData> dataList=null;
        Document document=Jsoup.parse(htmlStr);
        Element oneScript=document.getElementById("getAreaStat");
        String data=oneScript.data();
        data=data.substring(data.indexOf("["),data.lastIndexOf("]")+1);
        Gson gson=new Gson();
        List provinceList=gson.fromJson(data,List.class);
        if(save)
            return getDXYDataList(provinceList,null);
        dataList=parseJsonFromDXY(provinceList);
        return dataList;
    }
    private static String getAreaNameFromDXY(Map areaInfoMap)
    {
        String areaName= (String) areaInfoMap.get("provinceName");
        if(areaName==null || "".equals(areaName))
        {
            areaName= (String) areaInfoMap.get("cityName");
            if(areaName==null || "".equals(areaName)) return null;
        }
        return areaName;
    }
    private static List<SituationData> parseJsonFromDXY(List areaList)
    {
        if(areaList==null || areaList.size()==0) return null;
        List<SituationData> result=new ArrayList<>();
        for(int i=0;i<areaList.size();i++)
        {
            Map areaInfoMap= (Map) areaList.get(i);
            String areaName=getAreaNameFromDXY(areaInfoMap);
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
                    parseJsonFromDXY((List) areaInfoMap.get("cities")));
            result.add(oneData);
        }
        return result;
    }
    private static List<SituationData> getDXYDataList(List areaList, String pName)
    {
        if(areaList==null) return null;
        List<SituationData> result=new ArrayList<>();
        for(int i=0;i<areaList.size();i++)
        {
            Map areaInfoMap= (Map) areaList.get(i);
            String areaName=getAreaNameFromDXY(areaInfoMap);
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
                result.addAll(getDXYDataList(childrenList,areaName));
        }
        return result;
    }

    public static List<StatisticData> parseJson4StaData(String jsonStr,boolean save)
    {
        List<StatisticData> result=new ArrayList<>();
        Gson gson=new Gson();
        Map jsonMap=gson.fromJson(jsonStr,Map.class);
        Map dataMap= (Map) jsonMap.get("data");
        List chinaDayList= (List) dataMap.get("chinaDayList");
        for(int i=0;i<chinaDayList.size();i++)
        {
            Map dayInfoMap= (Map) chinaDayList.get(i);
            result.add(new StatisticData(null,
                    (String) dayInfoMap.get("date"),
                    ((Double) dayInfoMap.get("confirm")).intValue(),
                    ((Double) dayInfoMap.get("nowConfirm")).intValue(),
                    ((Double) dayInfoMap.get("nowSevere")).intValue(),
                    ((Double) dayInfoMap.get("importedCase")).intValue(),
                    ((Double) dayInfoMap.get("suspect")).intValue(),
                    ((Double) dayInfoMap.get("dead")).intValue(),
                    Float.parseFloat((String) dayInfoMap.get("deadRate")),
                    ((Double) dayInfoMap.get("heal")).intValue(),
                    Float.parseFloat((String) dayInfoMap.get("healRate"))));
        }
        return result;
    }

}
