package com.jaya.octovevent.service

import com.jaya.octovevent.db.DatabaseConnectionFactory.dbQuery
import com.jaya.octovevent.db.Events
import com.jaya.octovevent.dto.EventDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.joda.time.format.DateTimeFormat

class EventServiceImpl : EventService {

    private val dateFormat= DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    override suspend fun getEventsByIssueNumber(issueNumber: Int): List<EventDto> = dbQuery {
        Events.select {
            (Events.issueNumber eq issueNumber)
        }.mapNotNull { toEventDto(it) }
    }

    private fun toEventDto(event: ResultRow): EventDto? {

        return EventDto(
            id = event[Events.id],
            action = event[Events.action],
            createdAt = event[Events.createdAt].toString(dateFormat),
            issueNumber = event[Events.issueNumber]
        )
    }
}
