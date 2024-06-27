package me.basedatos2.pencaucu.schedulers;

import me.basedatos2.pencaucu.persistance.entities.Game;
import me.basedatos2.pencaucu.persistance.repositories.GameRepository;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class emailScheduler extends QuartzJobBean {

    private GameRepository gameRepository;

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
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getMergedJobDataMap().get("applicationContext");
        if (applicationContext == null) {
            throw new JobExecutionException("ApplicationContext is null");
        }
        this.gameRepository = applicationContext.getBean(GameRepository.class);

        System.out.println("ARRRRRRRRRANCA");
        JavaMailSender javaMailSender = getJavaMailSender();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("leonsalvo2012@gmail.com");
        message.setSubject("Recordatorio de PencaUCU");

        LocalDateTime l = LocalDateTime.now().plusDays(1);
        List<Game> gamesManiana = gameRepository.findManiana(l);

        if (gamesManiana.isEmpty()) {
            return;
        }

        StringBuilder messageText = new StringBuilder();
        messageText.append("Mañana juegan:\n");

        for (Game g : gamesManiana) {
            messageText.append(g.getTeam1id().getName() + " vs " + g.getTeam2id().getName() + " a las " + g.getTime() + " en " + g.getStadium() + "\n");
        }

        message.setText(messageText.toString());
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new JobExecutionException("Error enviando correo: " + e.getMessage(), e);
        }
    }
}
