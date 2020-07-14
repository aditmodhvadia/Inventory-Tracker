package com.fazemeright.myinventorytracker.di.module

import com.fazemeright.myinventorytracker.network.interfaces.SampleNetworkInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getRetrofit(factory: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://placeholder.com/api/")
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun getMoshiFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    @Singleton
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun getSampleApiService(retrofit: Retrofit): SampleNetworkInterface {
        return retrofit.create(SampleNetworkInterface::class.java)
    }
}
