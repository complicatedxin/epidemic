package com.sauvignon.epidemicstatistics.automation;

import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class DataSchema
{
    @Autowired
    private DataService dataService;

    private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    @PostConstruct
    @Scheduled(cron = "0 0 0/12 * * ? ")
    public void refreshData()
    {
        System.out.println("更新数据到数据库："+dateFormat.format(new Date()));
        List<SituationData> dataList=dataService.captureList(true);
        if(dataList==null)
        {
            System.out.println("数据库更新失败！");
            return;
        }
        dataService.remove(null);
        if(!dataService.saveBatch(dataList))
            System.out.println("数据库更新失败！");
    }
}
