package com.plazoleta.usermicroservice.infrastructure.repositories.postgres;

import com.plazoleta.usermicroservice.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
