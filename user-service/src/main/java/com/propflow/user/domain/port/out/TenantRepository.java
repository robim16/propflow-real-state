package com.propflow.user.domain.port.out;


import com.propflow.user.domain.model.Tenant;
import com.propflow.user.domain.model.vo.TenantId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TenantRepository {
    Mono<Tenant> save(Tenant tenant);
    Mono<Tenant> findById(TenantId tenantId);
    Flux<Tenant> findAll(TenantQuery query);
    Mono<Tenant> findByDocumentNumber(String documentNumber);

    record TenantQuery(
         String status,
         int    page,
         int    size
    ) {
        public static Builder builder() { return new Builder(); }

        public static class Builder {
            private String status;
            private int    page;
            private int    size;

            public Builder status(String status) { this.status = status; return this; }
            public Builder page(int page) { this.page = page; return this; }
            public Builder size(int size) { this.size = size; return this; }

            public TenantQuery build() { return new TenantQuery(status, page, size); }

        }
    }
}
