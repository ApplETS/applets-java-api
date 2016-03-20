package applets.etsmtl.ca.amc.jobs;

import applets.etsmtl.ca.amc.db.MusiqueDAO;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;

/**
 * Created by valentin-debris on 2016-02-10.
 */

public class SongsJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
    }
}
