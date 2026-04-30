package com.cloudjet.gatewayservice.filter;

import com.cloudjet.gatewayservice.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component("JwtFilter")
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-User-Email", email)
                .header("X-User-Role", role)
                .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }
}