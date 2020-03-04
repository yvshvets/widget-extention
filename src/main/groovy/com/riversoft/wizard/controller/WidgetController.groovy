package com.riversoft.wizard.controller

import com.riversoft.wizard.model.WidgetModel
import com.riversoft.wizard.service.WidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.EntityResponse
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import static org.springframework.web.reactive.function.server.DefaultEntityResponseBuilder.DefaultEntityResponse

@RestController
@RequestMapping('/api/widget')
class WidgetController {

    @Autowired private WidgetService widgetService

    @PostMapping
    Mono loadWidget(@RequestPart('file') List<FilePart> parts, Authentication authentication) {

        widgetService.addWidget(authentication.name, parts.find())
    }

    @GetMapping
    Flux<WidgetModel> allWidgets(Authentication authentication) {

        widgetService.getAll(authentication.name)
    }

    @PostMapping('/activate')
    Mono activateWidget(@RequestParam String id, Authentication authentication) {

        widgetService.activateWidgetForUser(authentication.name, id)
    }

    @PostMapping('/deactivate')
    Mono deActivateWidget(@RequestParam String id, Authentication authentication) {

        widgetService.deActivateWidgetForUser(authentication.name, id)
    }

    @GetMapping('/my')
    Mono<List<String>> myWidgets(Authentication authentication) {

        widgetService.myWidgets(authentication.name)
    }

    @GetMapping('{id}/content')
    ResponseEntity<String> getWidgetContent(Authentication authentication, @PathVariable String id) {

        ResponseEntity.ok()
                .header('content-type', 'application/javascript; charset=UTF-8')
                .body(widgetService.getContent(authentication.name, id))
    }
}
