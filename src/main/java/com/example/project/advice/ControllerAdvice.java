package com.example.project.advice;

import com.example.project.dto.ApiResponse;
import com.example.project.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AlreadyUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiResponse handleAlreadyUseException(AlreadyUseException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }


    @ExceptionHandler(value = InvalidTokenRequestException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public ApiResponse handleInvalidTokenRequestException(InvalidTokenRequestException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = MailSendException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ApiResponse handleMailSendException(MailSendException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = NotEnoughResourcesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResponse handleNotEnoughResourcesException(NotEnoughResourcesException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = UserLoginException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse handleUserLoginException(UserLoginException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = UserLogoutException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiResponse handleUserLogoutException(UserLogoutException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = UserRegistrationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ApiResponse handleUserRegistrationException(UserRegistrationException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResponse handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiResponse handleUnsupportedOperationException(UnsupportedOperationException ex, WebRequest request) {
        return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
    }

    private String resolvePathFromWebRequest(WebRequest request) {
        try {
            return ((ServletWebRequest) request).getRequest().getAttribute("javax.servlet.forward.request_uri").toString();
        } catch (Exception ex) {
            return null;
        }
    }

}