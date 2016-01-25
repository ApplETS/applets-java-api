package applets.etsmtl.ca.news.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by gnut3ll4 on 24/01/16.
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing Hello Job");
    }
}
