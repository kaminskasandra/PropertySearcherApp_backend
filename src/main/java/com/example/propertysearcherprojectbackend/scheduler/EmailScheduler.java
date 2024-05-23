package com.example.propertysearcherprojectbackend.scheduler;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.Mail;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.service.AppointmentService;
import com.example.propertysearcherprojectbackend.service.EmailService;
import com.example.propertysearcherprojectbackend.service.UserService;
import jakarta.transaction.Transactional;
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
    private final AppointmentService appointmentService;

    @Scheduled(cron = "0 0 7 * * *")
    @Transactional
    public void sendInformationEmail() throws UserNotFoundException {
        HashMap<String, List<Appointment>> mailToSend = new HashMap<>();

        for (User user : userService.getAllUsers()) {
            List<Appointment> appointments = new ArrayList<>();
            for (Appointment appointment : appointmentService.getAppointmentsByUserId(user.getUserId())) {
                if (appointment.getDateOfMeeting().getDayOfWeek().equals(LocalDate.now().getDayOfWeek())) {
                    appointments.add(appointment);
                }
            }
            mailToSend.put(user.getMail(), appointments);
        }

        for (Map.Entry<String, List<Appointment>> mails : mailToSend.entrySet()) {
            emailService.send(
                    Mail.builder()
                            .mailTo(mails.getKey())
                            .subject(SUBJECT)
                            .message("Currently you have " + mails.getValue().size() + " " + (mails.getValue().size() == 1 ? "meeting" : "meetings") + " scheduled for today")
                            .build()
            );
        }
    }
}