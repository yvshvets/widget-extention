package com.riversoft.wizard

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class Security {

    @Autowired UserRepository userRepository

    @Bean
    PasswordEncoder passwordEncoder() {
        new BCryptPasswordEncoder()
    }

    @Bean
    ReactiveUserDetailsService reactiveUserDetailsService() {
        new ReactiveUserDetailsService() {
            @Override
            Mono<UserDetails> findByUsername(String username) {
                userRepository
                        .findByUsername(username)
                        .map { new org.springframework.security.core.userdetails.User(username, it.password, []) }
            }
        }
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                .csrf().disable()
                .authorizeExchange {
                    it.pathMatchers(HttpMethod.POST, '/api/users').permitAll()
                    it.pathMatchers('/api/widget/**').permitAll()
                    it.anyExchange().authenticated()
                }
                .formLogin {
                    it.loginPage('/api/login')
                    it.authenticationSuccessHandler { w,e -> Mono.fromRunnable { w.exchange.response.statusCode = HttpStatus.OK } }
                    it.authenticationFailureHandler { w,e -> Mono.fromRunnable { w.exchange.response.statusCode = HttpStatus.UNAUTHORIZED } }
                }
                .exceptionHandling {
                    it.authenticationEntryPoint { w,e -> Mono.fromRunnable { w.response.statusCode = HttpStatus.UNAUTHORIZED } }
                }
                .logout {
                    it
                            .logoutUrl('/api/logout')
                            .logoutSuccessHandler { w,e -> Mono.fromRunnable { w.exchange.response.statusCode = HttpStatus.OK } }
                }
                .build()
    }
}
