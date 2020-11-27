package com.sauvignon.epidemicstatistics.service;

import com.sauvignon.epidemicstatistics.pojo.StatisticData;
import java.util.List;

public interface GraphService
{
    List<StatisticData> captureList(boolean save);
}
