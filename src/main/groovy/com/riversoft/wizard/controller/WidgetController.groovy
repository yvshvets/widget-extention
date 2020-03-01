package com.riversoft.wizard.controller

import com.riversoft.wizard.model.WidgetModel
import com.riversoft.wizard.service.WidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.parser.Part
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping('/api/widget')
class WidgetController {

    @Autowired private WidgetService widgetService

    @PostMapping
    Mono loadWidget(@RequestPart('file') List<FilePart> parts, Authentication authentication) {

        widgetService.addWidget(authentication.name, parts.find())
    }

    @GetMapping
    Flux<WidgetModel> allWidgets() {

        widgetService.getAll()
    }
}
