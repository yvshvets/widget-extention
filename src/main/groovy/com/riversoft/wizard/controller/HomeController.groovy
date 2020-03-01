package com.riversoft.wizard.controller

import com.riversoft.wizard.User
import com.riversoft.wizard.UserRepository
import com.riversoft.wizard.model.CreateUserModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping('/api')
class HomeController {

    @Autowired private UserRepository userRepository
    @Autowired private PasswordEncoder passwordEncoder

    @PostMapping('/users')
    Mono createUser(@RequestBody CreateUserModel createUserModel) {

        userRepository
                .findByUsername(createUserModel.username)
                .map { Mono.error(new Exception("User with $it.username already exist")) }
                .log()
                .switchIfEmpty (
                        userRepository.save(new User(
                                username: createUserModel.username,
                                password: passwordEncoder.encode(createUserModel.password)
                        )) as Mono<? extends Mono<Object>>
                )
    }
}
