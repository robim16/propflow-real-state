package com.propflow.user.domain.model;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.vo.ProfileStatus;
import com.propflow.user.domain.model.vo.UserId;
import lombok.Builder;
import lombok.Getter;
import lombok.With;

import java.time.Instant;

@Builder
@Getter
@With
public class Advisor {

    private final AdvisorId id;
    private final UserId userId;
    private       String          documentNumber;
    private       String          address;
    private ProfileStatus status;
    private Instant createdAt;
    private Instant               updatedAt;
}
