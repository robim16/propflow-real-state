package com.propflow.user.domain.port.out;

import com.propflow.user.domain.model.Landlord;
import com.propflow.user.domain.model.vo.LandlordId;
import com.propflow.user.domain.model.vo.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LandlordRepository {
    Mono<Landlord> save(Landlord landlord);
    Flux<Landlord> findAll(LandlordQuery query);
    Mono<Landlord> findById(LandlordId landlordId);
    Mono<Landlord> findByUserId(UserId userId);
    Mono<Landlord>  findByDocumentNumber(String documentNumber);
    Mono<Landlord> findByHashedAccountNumber(String accountNumberHash);

    record LandlordQuery(
            String advisorId,
            String status,
            int    page,
            int    size
    ) {
        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String advisorId;
            private String status;
            private int    page = 0;
            private int    size = 20;

            public Builder advisorId(String advisorId) { this.advisorId = advisorId; return this; }
            public Builder status(String status)        { this.status = status;       return this; }
            public Builder page(int page)               { this.page = page;           return this; }
            public Builder size(int size)               { this.size = size;           return this; }

            public LandlordQuery build() {
                return new LandlordQuery(advisorId, status, page, size);
            }
        }
    }
}
