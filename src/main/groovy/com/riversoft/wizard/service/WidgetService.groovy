package com.riversoft.wizard.service

import com.riversoft.wizard.model.WidgetModel
import groovy.util.logging.Slf4j
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Slf4j
@Service
@ConfigurationProperties(prefix = 'widget')
class WidgetService {

    String path = './widgets'

    Flux<WidgetModel> getAll() {

        Flux
                .fromArray(new File(path)?.listFiles() ?: new File[0])
                .map { new WidgetModel(
                        name        : it.name.split('_:_')[1],
                        size        : it.size() / 1024,
                        createDate  : new Date(),
                        author      : it.name.split('_:_')[0]
                ) }
    }

    Mono<Void> addWidget(String author, FilePart filePart) {

        filePart
                .transferTo(new File("$path/${author}_:_${filePart.filename()}".toString()))
                .then()
    }
}
