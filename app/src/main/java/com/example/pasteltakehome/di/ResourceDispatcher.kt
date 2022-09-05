package com.example.pasteltakehome.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: ResourceDispatcher)


enum class ResourceDispatcher {
    DEFAULT, IO, MAIN, UNCONFINED
}