package com.homework.controller

import com.homework.BaseWebMvcSpec
import com.homework.controller.CustomerController
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import spock.lang.Unroll

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest(controllers = [CustomerController])
class CustomerControllerSpec extends BaseWebMvcSpec {

    @Unroll
    def "should not allow to create a registration with an invalid name: #name"() {
        given:
        Map request = [
                address : 'test',
                id          : 0,
                name     : name
        ]

        when:
        def results = doRequest(
                post('/customer/create').contentType(APPLICATION_JSON).content(toJson(request))
        )

        then:
        results.andExpect(status().isUnprocessableEntity())

        and:
        results.andExpect(jsonPath('$.errors[0].code').value('MethodArgumentNotValidException'))
        results.andExpect(jsonPath('$.errors[0].path').value('name'))
        results.andExpect(jsonPath('$.errors[0].userMessage').value(userMessage))

        where:
        name      || userMessage
        null      || 'Name must be provided.'
        'I'       || 'Name must be at least 2 characters and at most 50 characters long.'
        ''        || 'Name must be at least 2 characters and at most 50 characters long.'
    }

}