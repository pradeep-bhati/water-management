package com.rubicon.application.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.application")
public class ApplicationProperties {

	private String name;
	/*time span after current time orders will be taken for*/
	//suppose current time is  05 pm and time-span is 2hrs then orders can be scheduled from 7pm going forward//
	private String timespan;
	/*time-interval in b/w job runs, if 15 job will run 15 minutes*/
	private String interval;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimespan() {
		return timespan;
	}
	public void setTimespan(String timespan) {
		this.timespan = timespan;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
}
