package com.sauvignon.epidemicstatistics.resolver;

import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ESLocaleResolver implements LocaleResolver
{
    @Override
    public Locale resolveLocale(HttpServletRequest request)
    {
        Locale locale=Locale.getDefault();
        String lan=request.getParameter("lan");
        if(StringUtils.isEmpty(lan)) return locale;
        String lanSplit[]=lan.split("_");
        if(lanSplit.length!=2) return locale;
        locale=new Locale(lanSplit[0],lanSplit[1]);
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)
    { }
}
