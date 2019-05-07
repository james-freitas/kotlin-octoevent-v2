package com.jaya.octovevent.db

import org.jetbrains.exposed.sql.Table

object Events : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val action = varchar("action", 150)
    val createdAt = datetime("createdAt")
    val issueNumber = integer("issueNumber")
}
