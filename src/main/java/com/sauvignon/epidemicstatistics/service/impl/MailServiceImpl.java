package com.sauvignon.epidemicstatistics.service.impl;

import com.sauvignon.epidemicstatistics.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService
{
    @Autowired
    private JavaMailSender mailSender;

    public void send()
    {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("Sauvignon Epidemic-Statistics");
        message.setText("Log: no log");

        message.setFrom("3027963569@qq.com");
        message.setTo("1969126209@qq.com");
        mailSender.send(message);

        System.out.println("-Mail Sent-");
    }
}
