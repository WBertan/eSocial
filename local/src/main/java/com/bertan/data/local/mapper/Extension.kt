package com.bertan.data.local.mapper

internal fun <T, U, Z> Pair<T?, U?>.letNotNull(block: (T, U) -> Z?): Z? =
    let { (t, u) ->
        if (t != null && u != null) {
            block(t, u)
        } else {
            null
        }
    }