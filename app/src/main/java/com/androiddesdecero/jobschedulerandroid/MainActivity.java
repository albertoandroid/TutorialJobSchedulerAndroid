package com.androiddesdecero.jobschedulerandroid;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btLanzarScheduleJob;
    private Button btCancelarShceduleJob;
    private final static int ID_SERVICIO = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLanzarScheduleJob = findViewById(R.id.btLanzarScheduleJob);
        btLanzarScheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName componentName = new ComponentName(getApplicationContext(),JobServiceEjemplo.class);
                JobInfo info;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    info = new JobInfo.Builder(ID_SERVICIO, componentName)
                            .setRequiresCharging(true)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                            .setPersisted(true)
                            .setMinimumLatency(5*1000)
                            .build();
                }else{
                    info = new JobInfo.Builder(ID_SERVICIO, componentName)
                            .setRequiresCharging(true)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                            .setPersisted(true)
                            .setPeriodic(5*1000)
                            .build();
                }
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultado = scheduler.schedule(info);
                if(resultado == JobScheduler.RESULT_SUCCESS){
                    Log.d("TAG", "Job Acabado");
                }else{
                    Log.d("TAG", "Job ha fallado");
                }
            }
        });

        btCancelarShceduleJob = findViewById(R.id.btCancelarScheduleJob);
        btCancelarShceduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                scheduler.cancel(ID_SERVICIO);
                Log.d("TAG", "Job Cancelado");
            }
        });
    }
}
