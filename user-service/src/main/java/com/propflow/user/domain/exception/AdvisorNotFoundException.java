package com.propflow.user.domain.exception;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.vo.UserId;

public class AdvisorNotFoundException extends RuntimeException {

    public AdvisorNotFoundException(AdvisorId advisorId) {
        super("Could not find advisor with id: " + advisorId);
    }

    public AdvisorNotFoundException(UserId userId) {
        super("Could not find advisor with id: " + userId);
    }
}
