package com.sauvignon.epidemicstatistics.service.impl;

import com.google.gson.Gson;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.service.ElaSearchService;
import com.sauvignon.epidemicstatistics.util.Constant;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class ElaSearchServiceImpl implements ElaSearchService
{
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient rHLClient;

    @Override
    public boolean reFresh(List<SituationData> situDataList) throws IOException
    {
        //1. 清空（删除）索引
        GetIndexRequest getIndexRequest = new GetIndexRequest(Constant.ES_INDEX);
        if(rHLClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT))
        {
            DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest(Constant.ES_INDEX);
            AcknowledgedResponse response = rHLClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            if(!response.isAcknowledged())
                return false;
        }
        //创建索引
        CreateIndexRequest createIndexRequest=new CreateIndexRequest(Constant.ES_INDEX);
        CreateIndexResponse createIndexResponse = rHLClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        if(createIndexResponse.isAcknowledged())//创建索引成功
        {
            //2. 导入新数据
            BulkRequest bulkRequest = new BulkRequest();
            for(int i=0;i<situDataList.size();i++)
            {
                bulkRequest.add(
                        new IndexRequest(Constant.ES_INDEX)
                                .source(new Gson().toJson(situDataList.get(i)), XContentType.JSON)
                );
            }
            BulkResponse bulkResponse = rHLClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if(!bulkResponse.hasFailures())
                return true;
        }
        return false;
    }

    @Override
    public SearchHits searchArea(String areaName)
    {
        SearchRequest searchRequest = new SearchRequest(Constant.ES_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery
                .should(QueryBuilders.matchQuery("areaName.keyword",areaName)
                        .analyzer(Constant.IK_MAX_WORD))
                .should(QueryBuilders.matchQuery("pName.keyword",areaName)
                        .analyzer(Constant.IK_MAX_WORD));
        sourceBuilder
                .query(boolQuery)
                .from(0)
                .size(60)
                .timeout(TimeValue.timeValueSeconds(40));
        searchRequest.source(sourceBuilder);

        SearchResponse response = null;
        try {
            response = rHLClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response!=null ? response.getHits() : null;
    }
}
