package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailSenderServiceImpl implements MessageSenderService {
    
	@Autowired
    private JavaMailSender mailSender;
    
    @Override
    @Async
    public void sendMessage(String toAddress,
                                String subject,
                                String body
    )throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dark.masucle@gmail.com");
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("Mail Send...");


    }

    }
