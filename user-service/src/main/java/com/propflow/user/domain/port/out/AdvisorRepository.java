package com.propflow.user.domain.port.out;

import com.propflow.user.domain.model.vo.AdvisorId;
import com.propflow.user.domain.model.Advisor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdvisorRepository {
    Mono<Advisor> findById(AdvisorId advisorId);
    Flux<Advisor> findAll(AdvisorQuery query);

    record AdvisorQuery(
        String status,
        int page,
        int size
    ) {
        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String status;
            private int    page = 0;
            private int    size = 20;

            public Builder status(String status) { this.status = status; return this; }
            public Builder page(int page) { this.page = page; return this; }
            public Builder size(int size) { this.size = size; return this; }

            public AdvisorQuery build() { return new AdvisorQuery(status,page,size); }
        }

    }
}
