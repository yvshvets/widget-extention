package com.riversoft.wizard


import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByUsername(String s)
}