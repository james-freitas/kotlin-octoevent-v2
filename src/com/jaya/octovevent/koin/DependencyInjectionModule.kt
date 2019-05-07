package com.jaya.octovevent.com.jaya.octovevent.koin

import com.jaya.octovevent.service.EventService
import com.jaya.octovevent.service.EventServiceImpl
import org.koin.dsl.module.module

val dependencyInjectionModule = module {
    single { EventServiceImpl() as EventService }
}