package com.sauvignon.epidemicstatistics.service;

import com.sauvignon.epidemicstatistics.pojo.SituationData;
import org.elasticsearch.search.SearchHits;
import java.io.IOException;
import java.util.List;

public interface ElaSearchService
{
    boolean reFresh(List<SituationData> situDataList) throws IOException;

    SearchHits searchArea(String areaName);
}
