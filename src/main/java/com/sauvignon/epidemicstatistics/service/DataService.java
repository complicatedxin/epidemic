package com.sauvignon.epidemicstatistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sauvignon.epidemicstatistics.pojo.AreaData;
import com.sauvignon.epidemicstatistics.pojo.SituationData;
import java.util.List;

public interface DataService extends IService<SituationData>
{
    List<SituationData> list4Show();
    List<SituationData> captureList(boolean save);
    public List<AreaData> getAreaImportedCaseList(Boolean sortByAsc);

}
