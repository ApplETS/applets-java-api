package applets.etsmtl.ca;

import javax.ws.rs.ApplicationPath;

import applets.etsmtl.ca.news.jobs.EventsJob;
import applets.etsmtl.ca.news.jobs.NewsJob;
import applets.etsmtl.ca.news.jobs.SourcesJob;
import org.glassfish.jersey.server.ResourceConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        register(new MyApplicationBinder());
        String packages = this.getClass().getPackage().getName();
        packages(true, packages);

        JobDetail sources_job = JobBuilder.newJob(SourcesJob.class)
                .withIdentity("sourcesjob", "group1").build();

        JobDetail events_job = JobBuilder.newJob(EventsJob.class)
                .withIdentity("eventsjob", "group2").build();

        JobDetail news_job = JobBuilder.newJob(NewsJob.class)
                .withIdentity("newsjob", "group3").build();

        Trigger trigger_sources = TriggerBuilder
                .newTrigger()
                .withIdentity("triggersources", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(5000).repeatForever())
                .build();

        Trigger trigger_events = TriggerBuilder
                .newTrigger()
                .withIdentity("triggerevents", "group2")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2500).repeatForever())
                .build();

        Trigger trigger_news = TriggerBuilder
                .newTrigger()
                .withIdentity("triggernews", "group3")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2500).repeatForever())
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
            scheduler.scheduleJob(sources_job, trigger_sources);
            scheduler.scheduleJob(events_job, trigger_events);
            scheduler.scheduleJob(news_job, trigger_news);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }



    }

}