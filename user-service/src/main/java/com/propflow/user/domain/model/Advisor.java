package com.propflow.user.domain.model;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.vo.DocumentType;
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
    private DocumentType documentType;
    private       String          documentNumber;
    private ProfileStatus status;
    private Instant createdAt;
    private Instant               updatedAt;

    public static Advisor reconstitute(
            AdvisorId    id,
            UserId       userId,
            DocumentType documentType,
            String       documentNumber,
            ProfileStatus status,
            Instant      createdAt,
            Instant      updatedAt
    ) {

      var advisor = new Advisor(
              id, userId, documentType, documentNumber, status,
              createdAt, updatedAt
      );

      return advisor;
    }
}
