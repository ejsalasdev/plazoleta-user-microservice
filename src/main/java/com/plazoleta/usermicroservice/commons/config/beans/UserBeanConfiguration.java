package com.plazoleta.usermicroservice.commons.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.plazoleta.usermicroservice.domain.ports.in.UserServicePort;
import com.plazoleta.usermicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.usermicroservice.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.usermicroservice.domain.ports.out.RestaurantServicePort;
import com.plazoleta.usermicroservice.domain.ports.out.UserPersistencePort;
import com.plazoleta.usermicroservice.domain.usecases.UserUseCase;
import com.plazoleta.usermicroservice.domain.utils.validation.UserValidatorChain;
import com.plazoleta.usermicroservice.infrastructure.adapters.external.RestaurantServiceAdapter;
import com.plazoleta.usermicroservice.infrastructure.adapters.persistence.UserPersistenceAdapter;
import com.plazoleta.usermicroservice.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.usermicroservice.infrastructure.repositories.postgres.RoleRepository;
import com.plazoleta.usermicroservice.infrastructure.repositories.postgres.UserRepository;
import com.plazoleta.usermicroservice.application.client.handler.FoodcourtHandlerClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserBeanConfiguration {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoderPort passwordEncoderPort;
    private final RoleRepository roleRepository;

    @Bean
    public UserPersistencePort userPersistencePort() {
        return new UserPersistenceAdapter(userRepository, userEntityMapper, roleRepository);
    }
    
    @Bean
    public UserValidatorChain userValidatorChain() {
        return new UserValidatorChain();
    }

    @Bean
    public RestaurantServicePort restaurantServicePort(FoodcourtHandlerClient foodcourtHandlerClient) {
        return new RestaurantServiceAdapter(foodcourtHandlerClient);
    }

    @Bean
    public UserServicePort userServicePort(
            UserPersistencePort userPersistencePort,
            UserValidatorChain userValidatorChain,
            AuthenticatedUserPort authenticatedUserPort,
            RestaurantServicePort restaurantServicePort
    ) {
        return new UserUseCase(
                userPersistencePort,
                passwordEncoderPort,
                userValidatorChain,
                authenticatedUserPort,
                restaurantServicePort
        );
    }
}
