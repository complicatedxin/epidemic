package com.sauvignon.epidemicstatistics;

import com.sauvignon.epidemicstatistics.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EpidemicStatisticsApplicationTests
{
    @Autowired
    private MailService mailService;

    @Test //OK
    public void t_mail()
    {
//        mailService.send();
    }

}
