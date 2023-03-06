package at.steinbacher.common.di

import at.steinbacher.common.Dispatchers
import org.koin.dsl.module

val commonModule = module {
    single { Dispatchers() }
}