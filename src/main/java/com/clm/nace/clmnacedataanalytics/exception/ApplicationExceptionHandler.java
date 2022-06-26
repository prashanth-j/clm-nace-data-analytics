package com.clm.nace.clmnacedataanalytics.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler extends Throwable {


    @ExceptionHandler(MultipartException.class)
    public Map<String, String> invalidArgument(MultipartException exception) {
        Map<String, String> exceptioMap = new HashMap<>();
        exceptioMap.put(exception.getMessage(), exception.getLocalizedMessage());
        return exceptioMap;
    }
}
