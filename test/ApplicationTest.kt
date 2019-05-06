package com.jaya.octovevent

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.features.*
import io.ktor.webjars.*
import java.time.*
import com.fasterxml.jackson.databind.*
import com.google.gson.Gson
import com.jaya.octovevent.dto.EventDto
import io.ktor.jackson.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import io.ktor.client.features.logging.*
import kotlin.test.*
import io.ktor.server.testing.*
import io.ktor.client.engine.mock.*
import kotlinx.coroutines.io.*
import io.ktor.client.call.*
import org.assertj.core.api.Assertions.assertThat

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testGettingEventsByIssueNumber() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/issues/1/events").apply {

                val eventDtoList = Gson().fromJson(response.content, Array<EventDto>::class.java).asList()
                assertEquals(response.status(), HttpStatusCode.OK)

                assertThat(eventDtoList).hasSize(2)

                assertEquals(1, eventDtoList[0].id )
                assertEquals("opened", eventDtoList[0].action )
                assertEquals("2019-03-24T21:40:18Z", eventDtoList[0].createdAt )
                assertEquals(1, eventDtoList[0].issueNumber )

                assertEquals( 2, eventDtoList[1].id )
                assertEquals("closed", eventDtoList[1].action )
                assertEquals("2019-03-28T21:40:18Z", eventDtoList[1].createdAt )
                assertEquals(1, eventDtoList[1].issueNumber )
            }
        }
    }
}
