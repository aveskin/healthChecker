package ru.aveskin.emailnotificationmicroservice.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class EmailService {
    private JavaMailSender emailSender;

    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
//        emailSender.send(message);
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyEmail() {
        String subject = "Daily notification";
        String text = "Put health metrics!";

        List<String> emails = getEmailList();

        emails.forEach(email -> {
            try {
//                sendMessage(email.toString(), subject, text);
                log.info("Daily notification was sent");
            } catch (MessagingException e) {
                log.error("Error while sending out email..{}", (Object) e.getStackTrace());
            }
        });
    }

    private List<String> getEmailList() {
        List<String> emailList = new ArrayList<>();
        emailList.add("test@gmail.com");
        return emailList;
    }
}