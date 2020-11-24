package com.sauvignon.epidemicstatistics.service.impl;

import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.service.DataService;
import com.sauvignon.epidemicstatistics.util.Constant;
import com.sauvignon.epidemicstatistics.util.HttpUtil;
import com.sauvignon.epidemicstatistics.util.SauUtil;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DataServiceImpl implements DataService
{
    public List<SituationData> getList4Show()
    {
        String infoStr=HttpUtil.doGet(Constant.TX_COVID19_SOURCE);
        if(infoStr==null || "".equals(infoStr))
        {
            infoStr=HttpUtil.doGet(Constant.DXY_COVID19_PAGE);
            if(infoStr==null || "".equals(infoStr))
                return null;
            return this.handelHTML(infoStr);
        }
        return this.handleJson(infoStr);
    }
    private List<SituationData> handleJson(String jsonStr)
    {
        return SauUtil.parseJson2List(jsonStr);
    }
    private List<SituationData> handelHTML(String htmlStr)
    {
        return SauUtil.parseHTML2List(htmlStr);
    }

}
