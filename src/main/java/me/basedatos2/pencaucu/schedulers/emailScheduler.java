package me.basedatos2.pencaucu.schedulers;

import me.basedatos2.pencaucu.services.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class emailScheduler implements Job {

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            emailService.sendEmails();
        } catch (Exception e) {
            throw new JobExecutionException("Error enviando correo: " + e.getMessage(), e);
        }
    }
}
