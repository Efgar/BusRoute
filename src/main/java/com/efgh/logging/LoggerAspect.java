package com.efgh.logging;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
	static final Logger LOG = LoggerFactory.getLogger(LoggerAspect.class);
	
	@Around("execution(* com.efgh..*ServiceImpl.*(..))")
	public Object logServiceAccess(ProceedingJoinPoint joinPoint) throws Throwable{
		Object returned = null;
		Object[] args = joinPoint.getArgs();
		List<String> arguments = new ArrayList<String>();
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				arguments.add(String.valueOf(args[i]));
			}
		}
		
		LOG.info("Executing: " + joinPoint);
		LOG.info("Arguments received: " + arguments.toString());
		
		
		try {
			returned = joinPoint.proceed();
			LOG.info("Call returned: " + joinPoint.proceed());
		} catch (Throwable e) {
			LOG.error("Exception thrown during call", e);
			throw e;
		}
		return returned;
	}

}