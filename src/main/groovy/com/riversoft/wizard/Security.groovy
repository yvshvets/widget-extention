package com.riversoft.wizard

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
                .formLogin {
                    it.loginPage('/api/login')
                    it.authenticationSuccessHandler { w,e -> Mono.fromCallable { w.exchange.response.statusCode = 200 } }
                    it.authenticationFailureHandler { w,e -> Mono.fromCallable { w.exchange.response.statusCode = 401 } }
                }
                .exceptionHandling {
                    it.authenticationEntryPoint { w,e -> Mono.fromCallable { w.response.statusCode = 401 } }
                }
                .logout {
                    it.logoutUrl('/api/logout')
                }
                .build()
    }
}
