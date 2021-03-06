package com.github.sparkzxl.core.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;

/**
 * description: 且免工具类
 *
 * @author charles.zhou
 * @date 2021-05-19 13:56:05
 */
public class AspectUtils {

    public static Method getTargetMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method agentMethod = methodSignature.getMethod();
        return pjp.getTarget().getClass().getMethod(agentMethod.getName(), agentMethod.getParameterTypes());
    }

    /**
     * 获取spel表达式结果
     *
     * @param joinPoint  切入点
     * @param expression 表达式
     * @return String
     * @throws NoSuchMethodException 方法找不到异常
     */
    public static String parseExpression(ProceedingJoinPoint joinPoint, String expression) throws NoSuchMethodException {
        Method targetMethod = getTargetMethod(joinPoint);
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new MethodBasedEvaluationContext(new Object(), targetMethod, joinPoint.getArgs(),
                new DefaultParameterNameDiscoverer());
        Expression parseExpression = parser.parseExpression(expression);
        return parseExpression.getValue(context, String.class);
    }
}
