package com.sauvignon.epidemicstatistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sauvignon.epidemicstatistics.mapper")
public class EpidemicStatisticsApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(EpidemicStatisticsApplication.class, args);
    }
}
