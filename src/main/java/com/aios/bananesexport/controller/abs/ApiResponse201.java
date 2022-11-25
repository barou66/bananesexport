package com.aios.bananesexport.controller.abs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponse(responseCode = "201", description = "Created successfully", content = {})
public @interface ApiResponse201 {

    @AliasFor(annotation = ApiResponse.class, attribute = "content") Content[] content() default @Content();

}
