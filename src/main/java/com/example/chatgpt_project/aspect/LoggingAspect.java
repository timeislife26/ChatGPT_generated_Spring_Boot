package com.example.chatgpt_project.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut to target methods in service layer
    @Pointcut("execution(* com.example.chatgpt_project.services.*.*(..))")
    public void serviceMethods() {}

    // Log before method execution
    @Before("serviceMethods()")
    public void logBeforeMethodExecution() {
        logger.info("Method execution started");
    }

    // Log after method execution
    @After("serviceMethods()")
    public void logAfterMethodExecution() {
        logger.info("Method execution completed");
    }

    // Log exceptions thrown by methods
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterThrowingException(Exception ex) {
        logger.error("Exception occurred: {}", ex.getMessage());
    }

    // Log method arguments and return values (if needed)
    // Example for logging method execution with parameters and return values
    @Before("execution(* com.example.chatgpt_project.services.HouseholdService.findByEircodeWithPets(..)) && args(eircode)")
    public void logBeforeFindHouseholdWithPets(String eircode) {
        logger.info("Executing findByEircodeWithPets with eircode: {}", eircode);
    }

    @After("execution(* com.example.chatgpt_project.services.HouseholdService.findByEircodeWithPets(..))")
    public void logAfterFindHouseholdWithPets() {
        logger.info("findByEircodeWithPets method execution completed");
    }
}
