package org.sdas.cloud.core;

import java.util.concurrent.TimeUnit;

public class CloudConfig {
	public final static int DEFAULT_THREAD_POOL_SIZE = 10;
	public final static int DEFAULT_TIME_PERIOD = 100;
	public final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
	public final static int DEFAULT_TIMEOUT_PERIOD = 100000;
	
	private int threadPoolSize;
	private int timePeriod;
	private TimeUnit timeUnit;
	
	public int getThreadPoolSize() {
		return threadPoolSize;
	}
	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
	public int getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	
}
