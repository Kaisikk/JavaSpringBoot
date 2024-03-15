package com.kaisikk.java.springboot.javaspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Kaisikk
 *
 * Сервис по отправке сообщений
 */
@Service
public class MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;


    /**
     * Отправка сообщения
     *
     * @param emailTo
     * @param subject
     * @param message
     */
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // от кого
        mailMessage.setFrom(username);
        // на чью почту
        mailMessage.setTo(emailTo);
        // тема
        mailMessage.setSubject(subject);
        // тело сообщения
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
