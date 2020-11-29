package com.sauvignon.epidemicstatistics.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
public class AreaData implements Serializable
{
    private String areaName;

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
