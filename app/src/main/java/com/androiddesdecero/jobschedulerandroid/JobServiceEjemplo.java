package com.androiddesdecero.jobschedulerandroid;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * Created by albertopalomarrobledo on 8/6/18.
 */

public class JobServiceEjemplo extends JobService {

    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("TAG", "onStartJob");
        doBackWork(jobParameters);
        return true;
    }

    private void doBackWork(final JobParameters jobParameters){
        Log.d("TAG", "doBackWork");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<10; i++){
                    if(jobCancelled){
                        return;
                    }
                    Log.d("TAG", "RUN: " +  i);
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){

                    }
                }
                Log.d("TAG", "Job Fisnished");
                jobFinished(jobParameters, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("TAG", "onStopJob");
        jobCancelled = true;
        return false;
    }
}
