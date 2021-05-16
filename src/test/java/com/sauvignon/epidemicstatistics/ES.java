package com.sauvignon.epidemicstatistics;

import com.sauvignon.epidemicstatistics.controller.SearchController;
import com.sauvignon.epidemicstatistics.util.Constant;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class ES
{

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient rHLClient;


    @Test
    public void t_search()
    {
        String searchInfo="山东";

        SearchRequest searchRequest = new SearchRequest(Constant.ES_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery
                .should(QueryBuilders.matchQuery("areaName",searchInfo))
                .should(QueryBuilders.matchQuery("pName",searchInfo));
        sourceBuilder
                .query(boolQuery)
                .from(0)
                .size(20)
                .timeout(TimeValue.timeValueSeconds(40));
        searchRequest.source(sourceBuilder);

        SearchResponse response = null;
        try {
            response = rHLClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SearchHits searchHits = response.getHits();
        if(searchHits==null) return;
        SearchHit hits[]= searchHits.getHits();
        Map<String, Map> dataMap=new HashMap<>(64);
        for(SearchHit document:hits)
        {
            Map fieldMap=document.getSourceAsMap();
            dataMap.put((String) fieldMap.get("areaName"),fieldMap);
        }

        System.out.println(dataMap);
    }
}
