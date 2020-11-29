package com.sauvignon.epidemicstatistics.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
public class EchartsMapData implements Serializable
{
    private String name;
    private Integer value;
}
