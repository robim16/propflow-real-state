package com.propflow.auth.infrastructure.adapter.persistence;


import com.propflow.auth.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {

    @Id
    private UUID id;
    private String name;
    private String   passwordHash;
    private UserRole role;
    private boolean  emailVerified;
    private boolean  active;
    private String   oauthProvider;
    private String   oauthProviderId;
    private Instant createdAt;
    private Instant  updatedAt;

}
