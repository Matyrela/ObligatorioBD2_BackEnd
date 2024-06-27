package me.basedatos2.pencaucu.config;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AutowiringSpringBeanJobFactory implements JobFactory, ApplicationContextAware {

    private transient ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
        return (Job) applicationContext.getBean(bundle.getJobDetail().getJobClass());
    }
}
