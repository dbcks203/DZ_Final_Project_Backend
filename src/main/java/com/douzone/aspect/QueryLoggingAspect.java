package com.douzone.aspect;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.session.SqlSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class QueryLoggingAspect {
	
    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
	private Environment environment;
    
    @Around("execution(* *..dao.*.*(..))")
    public Object logSqlAndParams(ProceedingJoinPoint joinPoint) throws Throwable {
    	String[] activeProfiles = environment.getActiveProfiles();
	    for (String profile : activeProfiles) {
	        if ("prod".equals(profile)) {
	            return joinPoint.proceed();
	        }
	    }
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        String sqlQuery = "";
        String statementId = sqlSession.getConfiguration().getMappedStatementNames()
                .stream()
                .filter(name -> name.endsWith("." + methodName))
                .findFirst()
                .orElse(null);
        
        if (methodArgs.length == 0) {
            log.warn("No method arguments found for method: {}", methodName);
            return joinPoint.proceed();
        }
        if (statementId == null) {
            log.warn("MappedStatement not found for method: {}", methodName);
            return joinPoint.proceed();
        }
       
        sqlQuery = sqlSession.getConfiguration().getMappedStatement(statementId).getBoundSql(methodArgs[0]).getSql();
        Object params = methodArgs[0];
       
        log.info("\nExecuting SQL query: {}", "\n\t"+sqlQuery);
        if (params instanceof ParamMap) {
            log.info("Parameters: {}", ((ParamMap<?>) params).entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((param1, param2) -> param1 + ", " + param2)
                    .orElse("None"));
        } else {
            log.info("Parameters: {}", params);
        }

        Object result = joinPoint.proceed();
        
        log.info("Result: {}", result);

        return result;
    }
}