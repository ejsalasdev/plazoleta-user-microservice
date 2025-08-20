package com.plazoleta.usermicroservice.infrastructure.repositories.postgres;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.infrastructure.entities.RolEntity;

public interface RoleRepository extends JpaRepository<RolEntity, Long> {

    Optional<RolEntity> findByName(RoleName name);
    
}
