package com.bertan.data.remote

internal fun randomDate() =
    LongRange(1546300800000, System.currentTimeMillis()).random()