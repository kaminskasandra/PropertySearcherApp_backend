package com.example.propertysearcherprojectbackend.scheduler;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.Mail;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.service.EmailService;
import com.example.propertysearcherprojectbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private static final String SUBJECT = "Reminder about scheduled meetings";
    private final UserService userService;
    private final EmailService emailService;


    @Scheduled(cron = "0 0 7 * * *")
    public void sendInformationEmail() {
        HashMap<String, List<Appointment>> mailToSend = new HashMap<>();

        for (User user : userService.getAllUsers()) {
            List<Appointment> appointments = new ArrayList<>();
            for (Appointment appointment : user.getAppointments()) {
                if (appointment.getDateOfMeeting().getDayOfWeek().equals(LocalDate.now().getDayOfWeek())) {
                    appointments.add(appointment);
                }
            }
            mailToSend.put(user.getMail(), appointments);
        }

        for (Map.Entry<String, List<Appointment>> mails : mailToSend.entrySet()) {
            emailService.send(
                    new Mail(
                            mails.getKey(),
                            SUBJECT,
                            "You currently have" + mails.getValue().size() + " " + (mails.getValue().size() == 1 ? "meetings" : "meeting") + " scheduled for today"
                    )
            );
        }
    }
}
