package com.cmpe.project.aspects;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.cmpe.project.constants.Constants;
import com.cmpe.project.controller.MvCController;
 
/**
 * @author NISHANT
 *
 */
@Aspect
@Component
public class SessionChecker
{
	@Around("execution(public * com.cmpe.project.controller.MvCController.*Jsp(..)) && target(mvc)")
	public String before(ProceedingJoinPoint pjp,MvCController mvc) throws Throwable 
	{
		System.out.println("**** PROJECT 273 ****  : Executing "+ pjp.getSignature().getName() +" method!" );
		HttpSession httpSession = mvc.getHttpSession();
		
		if(null == httpSession.getAttribute(Constants.LOGGED_IN_USER)) {
			httpSession.invalidate();
			return "Login";
		}
		else 
			return (String) pjp.proceed();
	}
}