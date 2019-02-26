package io.vepo.cdiexample.logged;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Logged
@Interceptor
public class LoggedInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(LoggedInterceptor.class);

	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		logger.info("Calling {} in {}...", invocationContext.getMethod().getName(),
				invocationContext.getMethod().getDeclaringClass().getName());
		Object value = invocationContext.proceed();
		logger.info("It returns {}! {} in {}", value, invocationContext.getMethod().getName(),
				invocationContext.getMethod().getDeclaringClass().getName());
		return value;
	}
}
