package com.ecommerce.service.email_service;

import com.ecommerce.payload.EmailDetails;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Autowired
    private SendGrid sendGrid;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Response sendSimpleMailUsingSendGrid(EmailDetails emailDetails)  {
        Email from = new Email("arunkumar828307@gmail.com");
        Email recipient = new Email(emailDetails.getRecipient());
        Content content = new Content("text/plain", emailDetails.getMsgBody());
        Mail mail = new Mail(from, emailDetails.getSubject(), recipient, content);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(mail.build());
            return sendGrid.api(request);
        } catch(IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public boolean sendSimpleMail(EmailDetails details) throws MailException{
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(details.getRecipient());
        smm.setSubject(details.getSubject());
        smm.setText(details.getMsgBody());
        javaMailSender.send(smm);
        return true;
    }
}

