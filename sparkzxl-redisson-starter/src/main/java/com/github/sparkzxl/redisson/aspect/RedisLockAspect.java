package com.github.sparkzxl.redisson.aspect;

import com.github.sparkzxl.redisson.annotation.RedisLock;
import com.github.sparkzxl.redisson.lock.RedisDistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * description:
 *
 * @author: zhouxinlei
 * @date: 2021-01-04 09:58:41
 */
@Aspect
public class RedisLockAspect {

    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @Pointcut("@annotation(redisLock)")
    public void redisLockAspect(RedisLock redisLock) {

    }

    @Around(value = "redisLockAspect(redisLock)", argNames = "proceedingJoinPoint,redisLock")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, RedisLock redisLock) {

        String keyPrefix = redisLock.keyPrefix();
        int lockFiled = redisLock.lockFiled();
        long waitTime = redisLock.waitTime();
        long leaseTime = redisLock.leaseTime();
        int tryCount = redisLock.tryCount();
        long sleepTime = redisLock.sleepTime();
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[lockFiled];
        String lockKey = keyPrefix.concat("_").concat(String.valueOf(arg));
        boolean lock = false;
        Object result = null;
        try {
            lock = redisDistributedLock.lock(lockKey, waitTime, leaseTime, tryCount, sleepTime);
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (lock) {
                redisDistributedLock.releaseLock(lockKey);
            }
        }
        return result;
    }
}