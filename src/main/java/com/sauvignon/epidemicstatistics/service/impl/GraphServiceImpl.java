package com.sauvignon.epidemicstatistics.service.impl;

import com.sauvignon.epidemicstatistics.pojo.StatisticData;
import com.sauvignon.epidemicstatistics.service.GraphService;
import com.sauvignon.epidemicstatistics.util.Constant;
import com.sauvignon.epidemicstatistics.util.HttpUtil;
import com.sauvignon.epidemicstatistics.util.SauUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphServiceImpl implements GraphService
{
    public List<StatisticData> captureList(boolean save)
    {
        String jsonStr=HttpUtil.doGet(Constant.TX_COVID19_STATISTIC);
        return SauUtil.captureStaDataFromTX(jsonStr,false);
    }
}
