package com.example.apiGateWay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.apiGateWay.zuul.filter.ErrorFilter;
import com.example.apiGateWay.zuul.filter.PostFilter;
import com.example.apiGateWay.zuul.filter.PreFilter;
import com.example.apiGateWay.zuul.filter.RouteFilter;


@SpringBootApplication
@EnableZuulProxy
public class ApiGateWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGateWayApplication.class, args);
	}

	@Bean
    public PreFilter preFilter() {
		System.out.println("ApiGateWayApplication.preFilter()--> pre Filter Object Created");
        return new PreFilter();
    }
    @Bean
    public PostFilter postFilter() {
		System.out.println("ApiGateWayApplication.postFilter()--> postFilter Filter Object Created");
        return new PostFilter();
    }
    @Bean
    public ErrorFilter errorFilter() {
		System.out.println("ApiGateWayApplication.errorFilter()--> errorFilter Filter Object Created");
        return new ErrorFilter();
    }
    @Bean
    public RouteFilter routeFilter() {
		System.out.println("ApiGateWayApplication.routeFilter()--> routeFilter Filter Object Created");
        return new RouteFilter();
    }
    
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
