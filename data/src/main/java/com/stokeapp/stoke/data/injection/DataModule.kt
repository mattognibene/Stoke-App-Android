package com.stokeapp.stoke.data.injection;

import com.stokeapp.stoke.cache.room.DatabaseModule;
import com.stokeapp.stoke.domain.executors.AppExecutors;
import com.stokeapp.stoke.remote.NetworkModule;

import java.util.concurrent.Executors

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module(includes = [DatabaseModule::class, NetworkModule::class])
object DataModule {
    @Provides
    @JvmStatic
    fun providesExecutors(): AppExecutors {
        return object : AppExecutors {
            override fun diskIo(): Scheduler {
                return Schedulers.from(Executors.newFixedThreadPool(3))
            }

            override fun networkIo(): Scheduler {
                return Schedulers.io()
            }

            override fun mainThread(): Scheduler {
                return AndroidSchedulers.mainThread()
            }
        }
    }
}
