package com.plazoleta.usermicroservice.infrastructure.entities;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RolEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15, unique = true)
    private RoleName name;
    
    @Column(nullable = false, length = 90)
    private String description;
}
