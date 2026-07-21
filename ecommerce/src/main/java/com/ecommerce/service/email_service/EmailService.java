package com.ecommerce.service.email_service;


import com.ecommerce.payload.EmailDetails;

public interface EmailService {
    boolean sendSimpleMail(EmailDetails details);
}

