package com.aios.bananesexport.controller.abs;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponse(responseCode = "204", description = "Deleted successfully", content = {})
public @interface ApiResponse204 {
}
