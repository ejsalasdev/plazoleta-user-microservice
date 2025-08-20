package com.plazoleta.usermicroservice.infrastructure.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.exceptions.ElementNotFoundException;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementFormatException;
import com.plazoleta.usermicroservice.domain.exceptions.InvalidElementLengthException;
import com.plazoleta.usermicroservice.domain.exceptions.RequiredFieldsException;
import com.plazoleta.usermicroservice.domain.exceptions.RoleNotAllowedException;
import com.plazoleta.usermicroservice.domain.exceptions.UnderAgeException;

@ControllerAdvice
public class ControllerAdvisor {

        @ExceptionHandler(ElementAlreadyExistsException.class)
        public ResponseEntity<ExceptionResponse> handleElementAlreadyExistsException(
                        ElementAlreadyExistsException exception) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(ElementNotFoundException.class)
        public ResponseEntity<ExceptionResponse> handleElementNotFoundException(ElementNotFoundException exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(InvalidElementFormatException.class)
        public ResponseEntity<ExceptionResponse> handleInvalidElementFormatException(
                        InvalidElementFormatException exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(RequiredFieldsException.class)
        public ResponseEntity<ExceptionResponse> handleRequiredFieldsException(RequiredFieldsException exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(InvalidElementLengthException.class)
        public ResponseEntity<ExceptionResponse> handleInvalidElementLengthException(
                        InvalidElementLengthException exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(UnderAgeException.class)
        public ResponseEntity<ExceptionResponse> handleUnderAgeException(UnderAgeException exception) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(RoleNotAllowedException.class)
        public ResponseEntity<ExceptionResponse> handleRoleNotAllowedException(RoleNotAllowedException exception) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new ExceptionResponse(exception.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler({
                        BadCredentialsException.class,
                        UsernameNotFoundException.class,
                        AuthenticationException.class
        })
        public ResponseEntity<ExceptionResponse> handleAuthenticationException(Exception exception) {
                String message = "Invalid username or password.";
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new ExceptionResponse(message, LocalDateTime.now()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception) {
                String message = "An unexpected error occurred. Please contact support if the problem persists.";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ExceptionResponse(message, LocalDateTime.now()));
        }
}
