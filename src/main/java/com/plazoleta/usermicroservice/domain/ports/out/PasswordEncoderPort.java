package com.plazoleta.usermicroservice.domain.ports.out;

public interface PasswordEncoderPort {
    
    String encode(String rawPassword);
}
