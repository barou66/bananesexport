package com.aios.bananesexport.controller.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ApiResponse(responseCode = "200", description = "Successful Operation", content = {})
public @interface ApiResponse200 {
    @AliasFor(annotation = ApiResponse.class, attribute = "content") Content[] content() default @Content();

}
