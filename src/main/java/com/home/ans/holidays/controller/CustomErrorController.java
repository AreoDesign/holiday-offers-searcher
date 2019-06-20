package com.home.ans.holidays.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class CustomErrorController implements ErrorController {

    private final static String ERROR_URL = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_URL;
    }

    @RequestMapping(value = ERROR_URL)
    public HttpStatus handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String statusCode = Optional.ofNullable(status.toString()).orElse("404");
        return HttpStatus.valueOf(statusCode);
    }
}
