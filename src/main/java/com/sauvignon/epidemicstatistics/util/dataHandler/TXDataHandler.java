package com.sauvignon.epidemicstatistics.util.dataHandler;

import com.google.gson.Gson;
import com.sauvignon.epidemicstatistics.pojo.AreaData;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.pojo.StatisticData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TXDataHandler
{
    public static List getProvinceList(String jsonStr)
    {
        Gson gson=new Gson();
        Map jsonMap=gson.fromJson(jsonStr, Map.class);
        jsonStr= (String) jsonMap.get("data");
        Map infoMap=gson.fromJson(jsonStr, Map.class);
        List areaTreeList= (List) infoMap.get("areaTree");
        Map areaTreeMap= (Map) areaTreeList.get(0);
        return  (List) areaTreeMap.get("children");
    }
    public static List<SituationData> parseSituData(List areaList)
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
                    parseSituData((List) areaInfoMap.get("children")));
            result.add(oneData);
        }
        return result;
    }
    public static List<SituationData> saveSituDataList(List areaList,String pName)
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
                result.addAll(saveSituDataList(childrenList,areaName));
        }
        return result;
    }

    public static List<StatisticData> parseStaData(String jsonStr)
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

    public static List<AreaData> abstractAreaData(List areaList,String objectiveName)
    {
        List<AreaData> result=new ArrayList<>();
        for(int i=0;i<areaList.size();i++)
        {
            Map areaInfoMap= (Map) areaList.get(i);
            String areaName= (String) areaInfoMap.get("name");
            List subAreaList= (List) areaInfoMap.get("children");
            if(subAreaList==null) continue;
            for(int j=0;j<subAreaList.size();j++)
            {
                Map subAreaInfoMap= (Map) subAreaList.get(j);
                String subAreaName= (String) subAreaInfoMap.get("name");
                if(objectiveName.equals(subAreaName))
                {
                    Map totalInfoMap= (Map) subAreaInfoMap.get("total");
                    result.add(new AreaData(areaName,
                            ((Double)totalInfoMap.get("confirm")).intValue(),
                            ((Double)totalInfoMap.get("nowConfirm")).intValue(),
                            null,null,null,
                            null,null,null,null));
                }
            }
        }
        return result;
    }

}
