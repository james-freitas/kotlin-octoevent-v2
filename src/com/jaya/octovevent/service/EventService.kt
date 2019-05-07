package com.jaya.octovevent.service

import com.jaya.octovevent.dto.EventDto

interface EventService {

    fun getEventsByIssueNumber(issueNumber: Int): List<EventDto>
}
