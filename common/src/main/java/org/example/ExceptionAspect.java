package org.example;

import com.mfa.framework.base.exception.ApplicationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

    @Around("execution(* org.example.service.*.*(..)) " +
            " || execution(* org.example.service2.*.*(..)) ")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
