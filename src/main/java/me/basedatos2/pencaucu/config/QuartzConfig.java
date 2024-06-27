package me.basedatos2.pencaucu.config;

import me.basedatos2.pencaucu.schedulers.emailScheduler;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public JobFactory jobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, JobDetail emailJobDetail, Trigger emailJobTrigger) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setJobDetails(emailJobDetail);
        factory.setTriggers(emailJobTrigger);
        return factory;
    }

    @Bean
    public JobDetail emailJobDetail() {
        return JobBuilder.newJob(emailScheduler.class)
                .withIdentity("emailJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger emailJobTrigger(JobDetail emailJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(emailJobDetail)
                .withIdentity("emailTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 14 * * ?"))
                .build();
    }
}
