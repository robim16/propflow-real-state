package com.propflow.user.infrastructure.entrypoint.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
@RequiredArgsConstructor
public class UserRouterRest {
    private final UserHandler handler;

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return RouterFunctions.route()

                .nest(path("/api/v1"), builder -> builder

                        .nest(path("/users"), userBuilder -> userBuilder
                                .GET("/me",              handler::getMyProfile)
                                .PUT("/me",              handler::updateMyProfile)

                                //documentos
                                .POST("/{id}/documents",              handler::requestUploadUrl)
                                .GET("/{id}/documents",               handler::listDocuments)
                                .PATCH("/{id}/documents/{docId}",     handler::verifyDocument)
                                .DELETE("/{id}/documents/{docId}",    handler::deleteDocument)
                        )
                        .nest(path("/landlords"), landlLordBuilder -> landlLordBuilder
                                .POST("",                         handler::createLandlord)
                                .GET("",                          handler::listLandlords)
                                .GET("/{id}",                     handler::getLandlord)
                                .PUT("/{id}",                     handler::updateLandlord)
                                .GET("/{id}/properties",          handler::getLandlordProperties)
                                .GET("/{id}/contracts",           handler::getLandlordContracts)
                        )
                        .nest(path("/tenants"), tenantBuilder -> tenantBuilder
                                .POST("",                           handler::createTenant)
                                .GET("",                            handler::listTenants)
                                .GET("/{id}",                       handler::getTenant)
                                .PUT("/{id}",                       handler::updateTenant)
                                .GET("/{id}/contracts",             handler::getTenantContracts)
                        )
                        .nest(path("/advisors"), advisorBuilder -> advisorBuilder
                                .GET("",                           handler::listAdvisors)
                                .GET("/{id}",                      handler::getAdvisor)
                                .PUT("/{id}",                      handler::updateAdvisor)
                                .GET("/{id}/assignments",          handler::getAdvisorAssignments)
                                .GET("/{id}/commissions",          handler::getAdvisorCommissions)
                                .POST("/{id}/assign",              handler::createAssignment)
                                .DELETE("/{id}/assign/{assignId}", handler::revokeAssignment)
                        )

                ).build();

    }

}
