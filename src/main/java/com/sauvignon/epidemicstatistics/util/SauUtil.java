package com.sauvignon.epidemicstatistics.util;

import com.google.gson.Gson;
import com.sauvignon.epidemicstatistics.pojo.AreaData;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.pojo.StatisticData;
import com.sauvignon.epidemicstatistics.util.dataHandler.DXYDataHandler;
import com.sauvignon.epidemicstatistics.util.dataHandler.TXDataHandler;
import java.util.List;

public class SauUtil
{
    public static List<SituationData> captureSituDataListFromTX(String jsonStr,boolean save)
    {
        List<SituationData> dataList=null;
        List provinceList=TXDataHandler.getProvinceList(jsonStr);
        if(!save)
            dataList=TXDataHandler.parseSituData(provinceList);
        else
            dataList=TXDataHandler.saveSituDataList(provinceList,null);
        return dataList;
    }
    public static List<SituationData> captureSituDataListFromDXY(String htmlStr,boolean save)
    {
        List<SituationData> dataList=null;
        List provinceList=DXYDataHandler.getProvinceList(htmlStr);
        if(!save)
            dataList=DXYDataHandler.parseSituData(provinceList);
        else
            dataList=DXYDataHandler.saveSituDataList(provinceList,null);
        return dataList;
    }

    public static List<StatisticData> captureStaDataFromTX(String jsonStr,boolean save)
    {
        return TXDataHandler.parseStaData(jsonStr);
    }

    public static List<AreaData> getAreaImportedCaseList(String jsonStr)
    {
        final String objectiveAreaName="境外输入";
        List provinceList=TXDataHandler.getProvinceList(jsonStr);
        return TXDataHandler.abstractAreaData(provinceList,objectiveAreaName);
    }

}
