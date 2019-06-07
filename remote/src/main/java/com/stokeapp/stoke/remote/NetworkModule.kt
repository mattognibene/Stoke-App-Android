package com.stokeapp.stoke.remote

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

@Module(includes = [NetworkSettings::class])
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun providesRetrofit(
        level: HttpLoggingInterceptor.Level
    ): Retrofit.Builder {
        val logging = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").v(message) }
            .apply { this.level = level }

        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        val moshi = Moshi.Builder()
                .add(AppJsonAdapterFactory.INSTANCE)
                .build()

        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Provides
    @JvmStatic
    fun providesOpenWeatherApi(
        builder: Retrofit.Builder
    ): OpenWeatherApi {
        val url = requireNotNull(HttpUrl.parse("https:/api.openweathermap.org/data/2.5/"))
        return builder.baseUrl(url)
                .build()
                .create(OpenWeatherApi::class.java)
    }
}