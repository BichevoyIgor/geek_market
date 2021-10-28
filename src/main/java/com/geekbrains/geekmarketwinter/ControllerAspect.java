package com.geekbrains.geekmarketwinter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ControllerAspect {

    @Around("execution(public * com.geekbrains.geekmarketwinter.services.ShoppingCartService.addToCart(..))")
    public void checkAddtoCart(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (!currentPrincipalName.equals("anonymousUser")){
            proceedingJoinPoint.proceed();
        }
//        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        for (SimpleGrantedAuthority authority : authorities) {
//            System.out.println(authority);
//        }
    }
}
