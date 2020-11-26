package com.sauvignon.epidemicstatistics.handler;

import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataHandler
{
    @Autowired
    private DataService dataService;

//    @PostConstruct
    public void refreshData()
    {
        System.out.println("更新数据到数据库...");
        List<SituationData> dataList=dataService.captureList(true);
        if(dataList==null)
        {
            System.out.println("数据库更新失败！");
            return;
        }
        dataService.remove(null);
        if(dataService.saveBatch(dataList))
            System.out.println("更新成功。");
    }
}
