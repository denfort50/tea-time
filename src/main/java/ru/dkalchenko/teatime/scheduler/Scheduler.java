package ru.dkalchenko.teatime.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.dkalchenko.teatime.model.EmailDetails;
import ru.dkalchenko.teatime.model.Person;
import ru.dkalchenko.teatime.service.EmailService;
import ru.dkalchenko.teatime.service.PersonService;

@Component
@AllArgsConstructor
public class Scheduler {

    private final EmailService emailService;
    private final PersonService personService;

    @Scheduled(cron = "0 0 17 ? * MON-FRI")
    public void scheduleTask() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        String strDate = dateFormat.format(new Date());
        System.out.println("Cron job Scheduler: Job running at - " + strDate);
        List<Person> people = personService.findAll();
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("Напоминание");
        emailDetails.setMsgBody("Пора пить чай!");
        people.forEach(person -> {
            emailDetails.setRecipient(person.getEmail());
            emailService.sendSimpleMail(emailDetails);
        });
    }
}
