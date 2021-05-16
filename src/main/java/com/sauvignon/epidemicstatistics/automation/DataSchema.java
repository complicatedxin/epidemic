package com.sauvignon.epidemicstatistics.automation;

import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.service.DataService;
import com.sauvignon.epidemicstatistics.service.ElaSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class DataSchema
{
    @Autowired
    private DataService dataService;
    @Autowired
    private ElaSearchService elaSearchService;

    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    @PostConstruct
    @Scheduled(cron = "0 0 0/12 * * ? ")
    public void refreshData()
    {
        List<SituationData> dataList=dataService.captureList(true);
        if(dataList==null)
        {
            System.out.println("数据捕获失败！");
            return;
        }

        dataService.remove(null);
        if(dataService.saveBatch(dataList))
            System.out.println("更新数据到：MySQL："+dateFormat.format(new Date()));
        else
            System.out.println("数据库更新失败！");

        try {
            if(elaSearchService.reFresh(dataList))
                System.out.println("更新数据到：ElasticSearch："+dateFormat.format(new Date()));
            else
                System.out.println("ElasticSearch更新失败！");
        } catch (IOException e) {
            log.info("【ElasticSearch】更新异常："+e.getMessage());
        }
    }
}
