package com;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.util.Pinger;

import io.timeandspace.cronscheduler.CronScheduler;



@SpringBootApplication
public class App {

	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		
		Duration syncPeriod = Duration.ofMinutes(20);
		CronScheduler cron = CronScheduler.create(syncPeriod);
		cron.scheduleAtFixedRateSkippingToLatest(0, 1, TimeUnit.MINUTES, runTimeMillis -> {
			Pinger.pingClient(); // ping client every 20 minutes to keep it from idling
		});
	}

}
