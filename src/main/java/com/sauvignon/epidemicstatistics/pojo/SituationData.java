package com.sauvignon.epidemicstatistics.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@TableName("stadata")
public class SituationData implements Serializable
{
    private Long sdId;
    private String areaName;
    private String pName;

    private Integer confirm;
    private Integer currentConfirm;
    private Integer suspect;
    private Integer dead;
    private Float deadRate;
    private Integer heal;
    private Float healRate;

    @TableField(exist = false)
    private List<SituationData> childrenList;
}
