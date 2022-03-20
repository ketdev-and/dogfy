package com.example.dog_app.qualifiers

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainThread()
