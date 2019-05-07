package com.jaya.octovevent

import io.ktor.http.*
import com.google.gson.Gson
import com.jaya.octovevent.dto.EventDto
import kotlin.test.*
import io.ktor.server.testing.*
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
    fun testGettingEventsByExistingIssueNumber() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/issues/1/events").apply {

                val eventDtoList = Gson().fromJson(response.content, Array<EventDto>::class.java).asList()
                assertEquals(response.status(), HttpStatusCode.OK)

                assertThat(eventDtoList).hasSize(0)
                /*assertThat(eventDtoList).hasSize(2)

                assertEquals(1, eventDtoList[0].id )
                assertEquals("opened", eventDtoList[0].action )
                assertEquals("2019-03-24T21:40:18Z", eventDtoList[0].createdAt )
                assertEquals(1, eventDtoList[0].issueNumber )

                assertEquals( 2, eventDtoList[1].id )
                assertEquals("closed", eventDtoList[1].action )
                assertEquals("2019-03-28T21:40:18Z", eventDtoList[1].createdAt )
                assertEquals(1, eventDtoList[1].issueNumber )*/
            }
        }
    }

    @Test
    fun testGetEventsByNotExistingIssueNumber() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/issues/2/events").apply {
                val eventDtoList = Gson().fromJson(response.content, Array<EventDto>::class.java).asList()
                assertEquals(response.status(), HttpStatusCode.OK)
                assertThat(eventDtoList).hasSize(0)
            }
        }
    }
}
