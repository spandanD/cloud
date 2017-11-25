package org.sdas.cloud.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.sdas.cloud.core.CloudConfig;
import org.sdas.cloud.core.RequestHandler;
import org.sdas.cloud.core.RequestHandlerPubSubImpl;

import java.util.concurrent.TimeUnit;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CloudService {

	Class<? extends RequestHandler> requestHandler() default RequestHandlerPubSubImpl.class;
	int threadPoolSize() default CloudConfig.DEFAULT_THREAD_POOL_SIZE;
	int timePeriod() default CloudConfig.DEFAULT_TIME_PERIOD;
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
	int timeoutPeriodd() default CloudConfig.DEFAULT_TIMEOUT_PERIOD;
	
}
