package com.ecommerce.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final JavaMailSender javaMailSender;

    public TestController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/send_email")
    public ResponseEntity<?> sendMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo("arunkumar700107@gmail.com");
        simpleMailMessage.setSubject("Test mail");
        simpleMailMessage.setText("Hello, This is test mail");

        try {
            javaMailSender.send(simpleMailMessage);
            return ResponseEntity.status(HttpStatus.OK).body("Email sent");
        } catch(Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email does not send");
        }

    }
}
