package com.homework

import com.homework.controller.ApiController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = HomeworkApplication.class)
class HomeworkApplicationSpecs extends Specification {

    @Autowired (required = false)
    private ApiController webController

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        webController
    }
}