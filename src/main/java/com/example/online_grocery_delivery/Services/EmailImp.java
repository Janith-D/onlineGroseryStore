package com.example.online_grocery_delivery.Services;

import com.example.online_grocery_delivery.Dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailImp implements EmailService{

   @Autowired
   private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Override
    public void sendEmail(EmailDto emailDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(emailDto.getRecipient());
            message.setText(emailDto.getMessageBody());
            message.setSubject(emailDto.getSubject());

            javaMailSender.send(message);
            System.out.println("Email send SUCCESSFUL!");
        }catch (MailException e){
            throw new RuntimeException(e);
        }
        }
    }

