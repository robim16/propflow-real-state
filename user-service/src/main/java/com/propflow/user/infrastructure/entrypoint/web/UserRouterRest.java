package com.propflow.user.infrastructure.entrypoint.web;

import com.propflow.user.infrastructure.entrypoint.web.advisor.AdvisorHandler;
import com.propflow.user.infrastructure.entrypoint.web.document.DocumentHandler;
import com.propflow.user.infrastructure.entrypoint.web.landlord.LandlordHandler;
import com.propflow.user.infrastructure.entrypoint.web.tenant.TenantHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouterRest {

    @Bean
    public RouterFunction<ServerResponse> landlordRoutes(LandlordHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/landlords",             handler::create)
                .GET("/api/v1/landlords",                  handler::list)
                .GET("/api/v1/landlords/{id}",          handler::getById)
                .PUT("/api/v1/landlords/{id}",          handler::update)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> tenantRoutes(TenantHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/tenants",                handler::create)
                .GET("/api/v1/tenants/{id}",             handler::getById)
                //.GET("/api/v1/tenants",                  handler::list)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> documentRoutes(DocumentHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/users/{id}/documents",            handler::requestUploadUrl)
                .PATCH("/api/v1/users/{id}/documents/{docId}",   handler::verify)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> advisorRoutes(AdvisorHandler handler) {
        return RouterFunctions.route()
                .POST("/api/v1/advisors/{id}/assign",                 handler::createAssignment)
                .DELETE("/api/v1/advisors/{id}/assign/{assignmentId}", handler::revokeAssignment)
                .GET("/api/v1/advisors/{id}/assignments",             handler::listAssignments)
                .build();
    }
}