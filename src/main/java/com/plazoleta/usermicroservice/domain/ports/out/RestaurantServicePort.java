package com.plazoleta.usermicroservice.domain.ports.out;

import java.util.Optional;

public interface RestaurantServicePort {
    
    Optional<Long> getRestaurantIdByOwnerId(Long ownerId);
}
