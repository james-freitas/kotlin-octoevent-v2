package com.jaya.octovevent

import io.ktor.http.*
import com.google.gson.Gson

import com.jaya.octovevent.dto.EventDto
import com.jaya.octovevent.koin.dependencyInjectionModule
import com.jaya.octovevent.service.EventService
import com.nhaarman.mockitokotlin2.KStubbing
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlin.test.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTest
import org.koin.test.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

class ApplicationTest : KoinTest {

    val eventService: EventService by inject()

    private val eventDtoList = listOfNotNull(
        EventDto(1, "opened", "2019-03-24T21:40:18Z", 1 ),
        EventDto(2, "closed", "2019-03-28T21:40:18Z", 1 )
    )

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
    fun `testGettingEventsByExistingIssueNumber with KoinTest`() {

        withTestApplication({ module(testing = true) }) {
            startKoin(listOf(dependencyInjectionModule))

            declareMock<EventService>()
            //{
            //    given(eventService.getEventsByIssueNumber(1)).willReturn(eventDtoList)
            //}

            handleRequest(HttpMethod.Get, "/issues/1/events").apply {
                val eventService:EventService = mock {
                    onBlocking { getEventsByIssueNumber(any()) } doReturn eventDtoList
                }

                val resultList = Gson().fromJson(response.content, Array<EventDto>::class.java).asList()

                assertEquals(response.status(), HttpStatusCode.OK)

                assertThat(resultList).hasSize(2)
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

/*
    fun <T : Any, R> KStubbing<T>.onBlocking(m:suspend  T.() -> R): OngoingStubbing<R> {
        return kotlinx.coroutines.runBlocking { Mockito.`when`(mock.m()) }
    }
*/


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
