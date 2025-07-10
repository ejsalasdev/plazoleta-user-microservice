package com.plazoleta.usermicroservice.infrastructure.adapters.external;

import java.util.Optional;

import com.plazoleta.usermicroservice.application.client.dto.RestaurantResponse;
import com.plazoleta.usermicroservice.application.client.handler.FoodcourtHandlerClient;
import com.plazoleta.usermicroservice.domain.ports.out.RestaurantServicePort;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantServiceAdapter implements RestaurantServicePort {
    
    private final FoodcourtHandlerClient foodcourtHandlerClient;
    
    @Override
    public Optional<Long> getRestaurantIdByOwnerId(Long ownerId) {
        try {
            RestaurantResponse restaurant = foodcourtHandlerClient.getRestaurantByOwnerId(ownerId);
            return Optional.of(restaurant.id());
        } catch (FeignException e) {
            // Restaurant not found or other Feign errors (server errors, connection issues, etc.)
            return Optional.empty();
        }
    }
}
