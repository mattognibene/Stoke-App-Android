package com.stokeapp.stoke.remote

import com.squareup.moshi.JsonAdapter
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
abstract class AppJsonAdapterFactory : JsonAdapter.Factory {
    companion object {
        @JvmField val INSTANCE: AppJsonAdapterFactory = KotshiAppJsonAdapterFactory()
    }
}