package me.basedatos2.pencaucu;

import lombok.AllArgsConstructor;
import me.basedatos2.pencaucu.schedulers.emailScheduler;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class PencaucuApplication {
	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(PencaucuApplication.class, args);

		JobDetail job = JobBuilder.newJob(emailScheduler.class)
				.withIdentity("email_service", "group1")
				.build();
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("email_service", "group1")
				//.withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))

				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 16 * * ?"))
				.build();

		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();

		scheduler.scheduleJob(job, trigger);


	}
}
