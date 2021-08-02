package com.vamkthesis.web.api;


import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.EmailDto;
import com.vamkthesis.web.dto.EmailSubcribeDto;
import com.vamkthesis.web.service.impl.EmailService;
import com.vamkthesis.web.service.impl.EmailSubcribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
public class EmailSubApi {
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailSubcribeService emailSubcribeService;

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity subscribe(@RequestBody EmailDto email) throws MessagingException {
        String message = String.format("<h2>Welcome to Thrive Shop</h2> </br>" +
                        "<p>Thank you for registering with Thrive</p></br>"
                        + "<p>Dear : %s</p></br>"
                        + "<p>As a new customer, we want to introduce you some features of our store that many customers find helpful.</p></br>"
                        + "<p>Also, through Your account, you can view and track information on our website, see recommendations, views wish list, and more.</p></br>"
                        + "<p>Stay Connected with us</p></br>"
                        + "<h3>Thank you for your subscribe.This is an automatically generated email, please do not reply </h3></br>"
                , email.getEmail());
        emailService.sendMail(email.getEmail(), "THANK YOU FOR YOUR SUBSCRIBE OUR SHOP", message);
        return ResponseEntityBuilder.getBuilder().setMessage("Email has been sent already !").build();
    }

    @RequestMapping(value = "/leavemessage", method = RequestMethod.POST)
    public ResponseEntity leaveMessage(@RequestBody EmailSubcribeDto emailSubcribeDto) throws MessagingException {
        EmailSubcribeDto emailSubcribeDto1 = emailSubcribeService.save(emailSubcribeDto);
        String message = String.format("<h2>Welcome to Thrive Shop</h2> </br>" +
                        "<p>Thank you for your message !</p></br>"
                        + "<p>Dear : %s</p></br>"
                        + "<p>WE USE THE DATA YOU VOLUNTARILY PROVIDE TO ANSWER YOUR QUESTIONS AND GUARANTEE THE ASSISTANCE REQUESTED. YOUR DATA WILL NOT BE USED FOR MARKETING PURPOSES AND WILL NOT BE DISCLOSED TO THIRD PARTIES. READ MORE.</p></br>"
                        + "<p>Stay Connected with us</p></br>"
                        + "<h3>This is an automatically generated email, please do not reply </h3></br>"
                , emailSubcribeDto.getEmail());
        emailService.sendMail(emailSubcribeDto.getEmail(), "THANK YOU FOR YOUR MESSAGE OUR SHOP", message);
        return ResponseEntityBuilder.getBuilder().setMessage("Save your message successfully").setDetails(emailSubcribeDto1).build();
    }


}
