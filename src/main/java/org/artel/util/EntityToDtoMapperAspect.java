package org.artel.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.artel.dto.CustomerDto;
import org.artel.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EntityToDtoMapperAspect {

    ModelMapper modelMapper;

    @Around("execution(* org.artel.service.CustomerService.*(..)) && args(user)")
    public Object mapToEntity(ProceedingJoinPoint joinPoint, User user) throws Throwable {
        Object result = joinPoint.proceed();
        return modelMapper.map(result, CustomerDto.class);
    }
}
