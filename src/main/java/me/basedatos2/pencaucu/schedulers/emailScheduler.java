package me.basedatos2.pencaucu.schedulers;

import lombok.RequiredArgsConstructor;
import me.basedatos2.pencaucu.persistance.entities.Game;
import me.basedatos2.pencaucu.persistance.repositories.GameRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@RequiredArgsConstructor
public class emailScheduler implements Job {

    final GameRepository gameRepository;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("mativaresc@gmail.com");
        mailSender.setPassword("boda mzoe jngo haug");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("MANDANDO!");
        JavaMailSender javaMailSender = getJavaMailSender();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("flopiroldos@gmail.com");
        message.setSubject("Recordatorio de penca");

        LocalDateTime l = LocalDateTime.now();
        List<Game> gamesMañana = gameRepository.findManiana(l);

        message.setText("Contenido del correo");

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new JobExecutionException("Error enviando correo: " + e.getMessage(), e);
        }
    }
}
