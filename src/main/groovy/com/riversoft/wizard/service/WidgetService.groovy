package com.riversoft.wizard.service


import com.riversoft.wizard.UserRepository
import com.riversoft.wizard.model.WidgetModel
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import java.math.RoundingMode

@Slf4j
@Service
@ConfigurationProperties(prefix = 'widget')
class WidgetService {

    @Autowired private UserRepository userRepository

    String path = './widgets'

    Flux<WidgetModel> getAll(String username) {

        userRepository
                .findByUsername(username)
                .zipWith( Mono.just(new File(path)?.listFiles() ?: new File[0]) )
                .map {
                    it.t2.collect { f ->
                        new WidgetModel(
                                id: f.name,
                                name: f.name.split('_:_')[1],
                                size: BigDecimal.valueOf(f.size() / 1024).setScale(2, RoundingMode.DOWN),
                                createDate: new Date(),
                                author: f.name.split('_:_')[0],
                                activated: it.t1.widgets.contains(f.name)
                        )
                    }
                }
                .flatMapIterable { it }
    }

    Mono<Void> addWidget(String author, FilePart filePart) {

        filePart
                .transferTo(new File("$path/${author}_:_${filePart.filename()}".toString()))
                .then()
    }

    Mono activateWidgetForUser(String username, String widgetId) {
        userRepository
                .findByUsername(username)
                .flatMap {
                    if (!it.widgets.contains(widgetId)) {
                        log.info("Activate widget ${widgetId} for user ${username}")
                        it.widgets << widgetId
                        return userRepository.save(it)
                    }
                    Mono.empty()
                }
    }

    Mono deActivateWidgetForUser(String username, String widgetId) {
        userRepository
                .findByUsername(username)
                .flatMap {
                    if (it.widgets.contains(widgetId)) {
                        log.info("Deactivate widget ${widgetId} for user ${username}")
                        it.widgets.remove(widgetId)
                        return userRepository.save(it)
                    }
                    Mono.empty()
                }
    }

    Mono<List<String>> myWidgets(String username) {
        userRepository
                .findByUsername(username)
                .map { it.widgets.toList() as List<String> }
    }

    String getContent(String username, String id) {

//        userRepository
//                .findByUsername(username)
//                .filter { it.widgets.contains(id) }
//                .map { new File("$path/$id").text }

        new File("$path/$id").text
    }
}
