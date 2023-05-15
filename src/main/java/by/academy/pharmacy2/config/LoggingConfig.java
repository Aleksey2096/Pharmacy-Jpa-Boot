package by.academy.pharmacy2.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static by.academy.pharmacy2.entity.Constant.E;
import static by.academy.pharmacy2.entity.Constant.ENTER_WITH_ARGUMENT_S;
import static by.academy.pharmacy2.entity.Constant.EXCEPTION_IN_WITH_CAUSE;
import static by.academy.pharmacy2.entity.Constant.EXIT_WITH_RESULT;
import static by.academy.pharmacy2.entity.Constant.JOIN_POINT_E;

@Component
@Aspect
public class LoggingConfig {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(by.academy.pharmacy2.entity.InfoLog)")
    public void infoLogPointcut() {
    }

    @Pointcut(
            "within(@org.springframework.stereotype.Service *) || within(@org.springframework.stereotype.Controller *)")
    public void springBeanPointcut() {
    }

    @Pointcut(
            "within(by.academy.pharmacy2.service..*) || within(by.academy.pharmacy2.controller..*)")
    public void applicationPackagePointcut() {
    }

    @Around("infoLogPointcut()")
    public Object aroundInfoLog(final ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(ENTER_WITH_ARGUMENT_S,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        logger.info(EXIT_WITH_RESULT,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
        return result;
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug(ENTER_WITH_ARGUMENT_S,
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        Object result = joinPoint.proceed();
        if (logger.isDebugEnabled()) {
            logger.debug(EXIT_WITH_RESULT,
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);
        }
        return result;
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()",
            throwing = E, argNames = JOIN_POINT_E)
    public void logAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        logger.error(EXCEPTION_IN_WITH_CAUSE,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause());
    }
}
