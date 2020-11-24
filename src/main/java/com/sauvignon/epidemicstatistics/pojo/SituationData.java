package com.sauvignon.epidemicstatistics.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class SituationData implements Serializable
{
    private Long id;
    private String name;

    private Integer confirm;
    private Integer currentConfirm;
    private Integer suspect;
    private Integer dead;
    private Float deadRate;
    private Integer heal;
    private Float healRate;

    private List<SituationData> childrenList;
}
