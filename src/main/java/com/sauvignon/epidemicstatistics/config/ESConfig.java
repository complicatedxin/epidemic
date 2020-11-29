package com.sauvignon.epidemicstatistics.config;

import com.sauvignon.epidemicstatistics.resolver.ESLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class ESConfig
{
    @Bean
    public LocaleResolver localeResolver()
    {
        return new ESLocaleResolver();
    }
}
