package ru.dkalchenko.teatime.service;

import ru.dkalchenko.teatime.model.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}
