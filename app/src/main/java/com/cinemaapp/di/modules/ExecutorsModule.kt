package com.cinemaapp.di.modules

import android.os.Handler
import android.os.Looper
import com.cinemaapp.utils.executors.AppExecutors
import com.cinemaapp.utils.executors.DiskIOThreadExecutor
import com.cinemaapp.utils.executors.MainThreadExecutor
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton


/*
* Created By mabrouk on 19/03/19
* KotilnApp
*/
@Module
class ExecutorsModule {
    @Provides
    @Singleton
    @Named("DiskIO")
    internal fun providesDiskIO(@Named("NetworkThreads") networkThreads: Int): Executor {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - networkThreads)
    }

    @Provides
    @Singleton
    @Named("DiskIOExecutor")
    internal fun providesDiskIOExecutor(@Named("DiskIO") diskIO: Executor): Executor {
        return DiskIOThreadExecutor(diskIO)
    }

    @Provides
    @Singleton
    @Named("NetworkIOExecutor")
    internal fun providesNetworkIOExecutor(@Named("NetworkThreads") numThreads: Int): Executor {
        return Executors.newFixedThreadPool(numThreads)
    }

    @Provides
    @Singleton
    @Named("MainThreadHandler")
    internal fun providesMainThreadHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }

    @Provides
    @Singleton
    @Named("MainThreadExecutor")
    internal fun providesMainThread(@Named("MainThreadHandler") mainThreadHandler: Handler): Executor {
        return MainThreadExecutor(mainThreadHandler)
    }

    @Provides
    @Singleton
    @Named("NetworkThreads")
    internal fun providesNumThreads(): Int {
        return 3
    }

    @Provides
    @Singleton
    internal fun providesAppExecutors(
        @Named("DiskIOExecutor") DiskIOExecutor: Executor, @Named("NetworkIOExecutor") NetworkIOExecutor: Executor, @Named(
            "MainThreadExecutor"
        ) MainThreadExecutor: Executor
    ): AppExecutors {
        return AppExecutors(DiskIOExecutor, NetworkIOExecutor, MainThreadExecutor)
    }
}
