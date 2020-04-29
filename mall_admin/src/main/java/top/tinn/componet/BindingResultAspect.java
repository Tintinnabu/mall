package top.tinn.componet;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import top.tinn.common.api.CommonResult;


/**
 * @Description BindingResultAspect HibernateValidator错误结果处理切面
 * @Author Tinn
 * @Date 2020/4/8 16:21
 */

@Aspect
@Component
@Order(2)
public class BindingResultAspect {
    @Pointcut("execution(public * top.tinn.controller.*.*(..))")
    public void BindingResult(){}

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        Object[] args=joinPoint.getArgs();
        for (Object arg:args){
            if (arg instanceof BindingResult){
                BindingResult result= (BindingResult) arg;
                if (result.hasErrors()){
                    FieldError fieldError=result.getFieldError();
                    if (fieldError!=null) return CommonResult.validateFailed(fieldError.getDefaultMessage());
                    return CommonResult.validateFailed();
                }
            }
        }
        return joinPoint.proceed();
    }

}
