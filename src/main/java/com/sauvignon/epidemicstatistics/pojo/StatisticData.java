package com.sauvignon.epidemicstatistics.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
@TableName("stadata")
public class StatisticData implements Serializable
{
    private Integer sdId;
    private String sdDate;

    private Integer confirm;
    private Integer currentConfirm;
    private Integer currentSevere;
    private Integer importedCase;
    private Integer suspect;
    private Integer dead;
    private Float deadRate;
    private Integer heal;
    private Float healRate;
}
