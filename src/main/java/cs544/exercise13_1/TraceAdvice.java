package cs544.exercise13_1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

import java.util.Date;

@Aspect
public class TraceAdvice {
    @After("execution(* cs544.exercise13_1.EmailSender.sendEmail(..))")
    public void traceForEmail(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object address = joinPoint.getArgs()[0];
        Object message = joinPoint.getArgs()[1];
        EmailSender server = (EmailSender) joinPoint.getTarget();

//        System.out.println(new Date()+" Method = "+ joinPoint.getSignature().getName());
//        System.out.println(new Date()+" Method = "+ methodName + " Address = "
//                + address+" Message = "+ message);
        System.out.println(new Date()+" Method = "+ methodName + ", Address = "
                + address+", Message = "+ message + ", Server = " + server.outgoingMailServer);
    }

    @Around("execution(* cs544.exercise13_1.EmailSender.sendEmail(..))")
    public Object invoke(ProceedingJoinPoint call ) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start(call.getSignature().getName());
        Object retVal = call.proceed();
        sw.stop();

        long totalTime = sw.getLastTaskTimeMillis();
        System.out.println("Time to execute " + totalTime + "ms");

        return retVal;
    }

}
