package com.ecommerce.service.email_service;


import com.ecommerce.payload.EmailDetails;
import com.sendgrid.Response;

import java.io.IOException;

public interface EmailService {
    boolean sendSimpleMail(EmailDetails details) ;
    Response sendSimpleMailUsingSendGrid(EmailDetails emailDetails);
}

