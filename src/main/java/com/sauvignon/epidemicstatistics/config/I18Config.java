package com.sauvignon.epidemicstatistics.config;

import com.sauvignon.epidemicstatistics.resolver.I18LocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class I18Config
{
    @Bean
    public LocaleResolver localeResolver()
    {
        return new I18LocaleResolver();
    }
}
