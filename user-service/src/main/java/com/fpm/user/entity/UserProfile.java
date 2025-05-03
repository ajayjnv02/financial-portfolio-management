package com.fpm.user.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    private String id; // Corresponds to Keycloak's user ID

    private String email;
    private String fullName;
    private String phoneNumber;
    private String country;
}
