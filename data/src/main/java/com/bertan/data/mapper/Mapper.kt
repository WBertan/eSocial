package com.bertan.data.mapper

internal fun <T, Z> T?.mapTo(mapper: (T) -> Z): Z? = this?.let(mapper)