package com;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.db.Database;
import com.util.Pinger;

import io.timeandspace.cronscheduler.CronScheduler;



@SpringBootApplication
public class App {

	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
		Database db = Database.getInstance();
		Duration syncPeriod = Duration.ofSeconds(1);
		CronScheduler cron = CronScheduler.create(syncPeriod);
		cron.scheduleAtFixedRateSkippingToLatest(0, 20, TimeUnit.MINUTES, runTimeMillis -> {
			Pinger.pingClient(); // ping client every 20 minutes to keep it from idling
		});
		
		cron.scheduleAtFixedRateSkippingToLatest(0, 50, TimeUnit.SECONDS, runTimeMillis -> {
			db.pingDB(); // ping DB every 50 seconds since ClearDB idles after a minute of inactivity
			System.out.println("Pinged DB");
		});
		
		EmailSender.notifyStarted();
	}

}
