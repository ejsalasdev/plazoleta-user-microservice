package com.plazoleta.usermicroservice.application.dto.response;

import java.time.LocalDateTime;

public record SaveUserResponse(String message, LocalDateTime timeStamp) {
}
