package com.stokeapp.stoke.domain.common

interface Mapper<T, R> {
    fun map(t: T): R
}