package applets.etsmtl.ca;


import applets.etsmtl.ca.news.jobs.SourcesJob;
import com.sun.jersey.api.model.AbstractResourceModelContext;
import com.sun.jersey.api.model.AbstractResourceModelListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.ws.rs.ext.Provider;

/**
 * Created by gnut3ll4 on 24/01/16.
 */
@Provider
public class StartupListener implements AbstractResourceModelListener {

    @Override
    public void onLoaded(AbstractResourceModelContext modelContext) {
        System.out.println("STARTUP");

        JobDetail job = JobBuilder.newJob(SourcesJob.class)
                .withIdentity("dummyJobName", "group1").build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(15).repeatForever())
                .build();


        /*
        //For a more cron-like syntax
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("dummyTriggerName", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
        */

        try {
            // schedule it
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }
}