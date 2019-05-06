package com.jaya.octovevent.service

import com.jaya.octovevent.dto.EventDto

class EventServiceImpl : EventService {

    private val eventList1 = listOfNotNull(
        EventDto(1, "opened", "2019-03-24T21:40:18Z", 1 ),
        EventDto(2, "closed", "2019-03-28T21:40:18Z", 1 )
    )

    private val map = mapOf(1 to eventList1, 2 to emptyList())

    override fun getEventsByIssueNumber(issueNumber: Int): List<EventDto> {
        return map[issueNumber] ?: emptyList()
    }
}
