package com.citypaths.web;

import com.citypaths.com.citypaths.model.CityPathsException;
import com.citypaths.com.citypaths.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value={CityPathsException.class})
    public final ResponseEntity<ErrorMessage> customHandleException(CityPathsException e , WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value() , e.getMessage() , "An application error has occured, please contact support");
        if (e.getLocalizedMessage() != null && ! e.getLocalizedMessage().isEmpty()) errorMessage.setErrorMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders() , HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
