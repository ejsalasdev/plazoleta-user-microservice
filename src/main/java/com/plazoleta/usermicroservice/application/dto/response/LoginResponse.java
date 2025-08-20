package com.plazoleta.usermicroservice.application.dto.response;

import java.time.LocalDateTime;

public record LoginResponse(
        String token,
        LocalDateTime timeStamp
) {
}
