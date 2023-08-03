package com.sky.acpect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.acpect
 * @Author: ShiJun
 * @CreateTime: 2023-08-03  12:55
 * @Description: TODO
 * @Version: 1.0
 */
/*
*    自定义切面  实现公共字段填充处理逻辑
* */

@Aspect
@Component
@Slf4j
public class AutofillAspect {

    /*切入点*/
    @Pointcut("execution(* com.sky.mapper.*.*(..)) &&  @annotation(com.sky.annotation.AutoFill)")
    public void  autoFillPointcut(){}
    /*  通知 */
    @Before("autoFillPointcut()")
    public  void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始进行公共字段进行自动填充");
        //获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();
        //获取到当前被拦截的方法参数
        Object[] args = joinPoint.getArgs();
        if ( args == null || args.length==0){
            return  ;
        }
        Object entity = args[0];

        //获取赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //根据不同的操作类型，为对应的属性进行反射赋值
        if (operationType ==OperationType.INSERT){
            /*为4个公共字段赋值*/
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            //通过反射为对象赋值
            setCreateTime.invoke(entity,now);
            setCreateUser.invoke(entity,currentId);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }else  if (operationType == OperationType.UPDATE){
            /*为2个公共字段赋值*/
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }

    }
}