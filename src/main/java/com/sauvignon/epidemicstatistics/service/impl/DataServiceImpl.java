package com.sauvignon.epidemicstatistics.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sauvignon.epidemicstatistics.mapper.DataMapper;
import com.sauvignon.epidemicstatistics.pojo.AreaData;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import com.sauvignon.epidemicstatistics.service.DataService;
import com.sauvignon.epidemicstatistics.util.Constant;
import com.sauvignon.epidemicstatistics.util.HttpUtil;
import com.sauvignon.epidemicstatistics.util.SauUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataServiceImpl extends ServiceImpl<DataMapper,SituationData> implements DataService
{
    public List<SituationData> captureList(boolean save)
    {
        String infoStr=HttpUtil.doGet(Constant.TX_COVID19_SOURCE);
        if(infoStr==null || "".equals(infoStr))
        {
            infoStr=HttpUtil.doGet(Constant.DXY_COVID19_PAGE);
            if(infoStr==null || "".equals(infoStr))
                return null;
            return SauUtil.captureSituDataListFromDXY(infoStr,save);
        }
        return SauUtil.captureSituDataListFromTX(infoStr,save);
    }

    public List<SituationData> list4Show()
    {
        List<SituationData> crudeList=list();
        HashMap<String,SituationData> map=new HashMap<>(32);
        for(SituationData sD:crudeList)
        {
            String pName=sD.getPName();
            if(pName==null || "".equals(pName))
                map.put(sD.getAreaName(),sD);
            else
            {
                List<SituationData> childrenList=map.get(pName).getChildrenList();
                if(childrenList==null)
                    childrenList=new ArrayList<>();
                childrenList.add(sD);
            }
        }
        return new ArrayList<SituationData>(map.values());
    }

    public List<AreaData> getAreaImportedCaseList(Boolean sortByAsc)
    {
        String jsonStr=HttpUtil.doGet(Constant.TX_COVID19_SOURCE);
        List<AreaData> areaDataList=SauUtil.getAreaImportedCaseList(jsonStr);
        if(sortByAsc==null) return areaDataList;
        else if(!sortByAsc)
            Collections.sort(areaDataList,(o1,o2)->o2.getConfirm()-o1.getConfirm());
        else
            Collections.sort(areaDataList,(o1,o2)->o1.getConfirm()-o2.getConfirm());
        return areaDataList;
    }

}
