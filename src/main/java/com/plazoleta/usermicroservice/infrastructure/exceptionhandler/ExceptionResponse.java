package com.plazoleta.usermicroservice.infrastructure.exceptionhandler;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}
