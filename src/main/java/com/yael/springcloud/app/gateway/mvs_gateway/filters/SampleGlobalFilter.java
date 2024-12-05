package com.yael.springcloud.app.gateway.mvs_gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;



@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {


    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("jecutando el filtro antes del request PRE");
        exchange.getRequest().mutate().headers(h -> h.add("token", "abcd"));

        return chain.filter(exchange)
            .then(Mono.fromRunnable( () -> {
                logger.info("Ejecutando filtro post response");
                
                Optional.ofNullable(
                    exchange.getRequest().getHeaders().getFirst("token")
                ).ifPresent(v -> {
                    logger.info("token: " + v);
                    exchange.getResponse().getHeaders().add("token", v);
                });

                exchange.getResponse().getCookies().add("color", ResponseCookie.from("Color", "Red").build());
                exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
            }));
    }

    @Override
    public int getOrder() { // se ordenan
        return 100;
    }

}
