package com.yummet.service.aop;

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
public class UserControllerAOPImpl {

	/**
	 * Get the logger class
	 * */
	private static final Logger logger = LoggerFactory.getLogger(UserControllerAOPImpl.class);

	/**
	 * Before UserController's operations
	 * */
	@Pointcut("execution(* com.yummet.bean.rest.controller.UserController.*(..))")
	public void UserControllerBeforePointcutter() {
	}

	/**
	 * Before UserController's operations
	 * */
	@Pointcut("execution(* com.yummet.bean.rest.controller.UserController.*(..))")
	public void UserControllerAfterPointcutter() {
	}

	/**
	 * Before and after usercontroller's operation with business logic
	 * */
	@Pointcut("execution(* com.yummet.bean.rest.controller.UserController.*(..))")
	public void UserControllerAround() {
	}

	/**
	 * perform any operation here such as conversion, authentication etc.
	 * */
	@Before("UserControllerBeforePointcutter()")
	public void doBefore(JoinPoint joinPoint) {
		logger.info("Entering : " + joinPoint.getSignature().getName());
		logger.debug("Entering : " + joinPoint.getSignature().getName());
	}

	/**
	 * maintain log, security, notification etc.
	 * */
	@After("UserControllerAfterPointcutter()")
	public void doAfter(JoinPoint joinPoint) {
		logger.info("Leaving :" + joinPoint.getSignature().getName());
	}

	/**
	 * Perform advice on return result, cache, analysis, performance
	 * */
	@AfterReturning(pointcut = "execution(* com.yummet.bean.rest.controller.UserController.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("Method Signature: " + joinPoint.getSignature());
		logger.info("Result in advice: " + result);
	}

	/***
	 * Around business logic of UserController's operations
	 * 
	 * @throws Throwable
	 * */
	@Around("UserControllerAround()")
	public Object aroundUserControllerOperation(
			ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		logger.info("Entering Around " + proceedingJoinPoint.getSignature());
		Object object = proceedingJoinPoint.proceed();
		return object;
	}

	/**
	 * Deal with throwables
	 * */
	@AfterThrowing(pointcut = "execution(* com.yummet.bean.rest.controller.UserController.*(..))", throwing = "throwable")
	public void userControllerThrowables(JoinPoint joinPoint,
			Throwable throwable) {
		logger.info("Method Signature: " + joinPoint.getSignature());
		logger.info("Exception is: " + throwable);
	}

}
