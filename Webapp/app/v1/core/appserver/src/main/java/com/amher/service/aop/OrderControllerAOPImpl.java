package com.amher.service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yucheng
 * @version 1
 * */
@Aspect
public class OrderControllerAOPImpl {
	/**
	 * Get the logger class
	 * */
	private static final Logger logger = LoggerFactory.getLogger(OrderControllerAOPImpl.class);

	/**
	 * Before OrderController's operations
	 * */
	@Pointcut("execution(* com.amher.bean.rest.controller.OrderController.*(..))")
	public void OrderControllerBeforePointcutter() {
	}

	/**
	 * Before OrderController's operations
	 * */
	@Pointcut("execution(* com.amher.bean.rest.controller.OrderController.*(..))")
	public void OrderControllerAfterPointcutter() {
	}

	/**
	 * Before and after OrderController's operation with business logic
	 * */
	@Pointcut("execution(* com.amher.bean.rest.controller.OrderController.*(..))")
	public void OrderControllerAround() {
	}

	/**
	 * perform any operation here such as conversion, authentication etc.
	 * */
	@Before("OrderControllerBeforePointcutter()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("Entering : " + joinPoint.getSignature().getName());
		logger.debug("Entering : " + joinPoint.getSignature().getName());
	}

	/**
	 * maintain log, security, notification etc.
	 * */
	@After("OrderControllerAfterPointcutter()")
	public void doAfter(JoinPoint joinPoint) {
		logger.info("Leaving :" + joinPoint.getSignature().getName());
	}

	/**
	 * Perform advice on return result, cache, analysis, performance
	 * */
	@AfterReturning(pointcut = "execution(* com.amher.bean.rest.controller.OrderController.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("Method Signature: " + joinPoint.getSignature());
		logger.info("Result in advice: " + result);
	}

	/***
	 * Around business logic of OrderController's operations
	 * 
	 * @throws Throwable
	 * */
	@Around("OrderControllerAround()")
	public Object aroundOrderControllerOperation(
			ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		logger.info("Entering Around " + proceedingJoinPoint.getSignature());
		Object object = proceedingJoinPoint.proceed();
		return object;
	}

	/**
	 * Deal with throwables
	 * */
	@AfterThrowing(pointcut = "execution(* com.amher.bean.rest.controller.OrderController.*(..))", throwing = "throwable")
	public void OrderControllerThrowables(JoinPoint joinPoint,
			Throwable throwable) {
		logger.info("Method Signature: " + joinPoint.getSignature());
		logger.info("Exception is: " + throwable);
	}
}
