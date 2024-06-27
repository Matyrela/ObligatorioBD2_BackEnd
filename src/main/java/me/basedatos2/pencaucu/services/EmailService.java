package me.basedatos2.pencaucu.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.basedatos2.pencaucu.persistance.entities.Game;
import me.basedatos2.pencaucu.persistance.entities.Student;
import me.basedatos2.pencaucu.persistance.repositories.GameRepository;
import me.basedatos2.pencaucu.persistance.repositories.StudentRepository;
import org.quartz.JobExecutionException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Service
@Log4j2
@RequiredArgsConstructor
public class EmailService {

    final GameRepository gameRepository;
    final StudentRepository studentRepository;

    public void sendEmails() throws Exception {
        log.info("Scheduler de envío de correos iniciado");

        List<Student> students = studentRepository.getStudents();

        LocalDateTime l = LocalDateTime.now().plusDays(1);
        List<Game> gamesManiana = gameRepository.findManiana(l);

        if (gamesManiana.isEmpty()) {
            System.out.println("No hay partidos mañana");
            System.out.println("No se envía correo");
            return;
        }

        JavaMailSender javaMailSender = getJavaMailSender();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Recordatorio de PencaUCU");

        StringBuilder messageText = new StringBuilder();
        messageText.append("Mañana juegan:\n");

        for (Game g : gamesManiana) {
            messageText.append(g.getTeam1id().getName() + " vs " + g.getTeam2id().getName() + " a las " + g.getTime() + " en " + g.getStadium() + "\n");
        }

        message.setText(messageText.toString());


        for (Student s : students) {
            message.setTo(s.getEmail());

            try {
                javaMailSender.send(message);
            } catch (Exception e) {
                throw new JobExecutionException("Error enviando correo: " + e.getMessage() + " a " + s.getEmail(), e);
            }
        }

        log.info("Scheduler de envío de correos finalizado");
    }

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
}
