package com.example.nycschools.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.nycschools.data.database.SchoolDatabase
import com.example.nycschools.data.remote.SchoolApiService
import com.example.nycschools.data.remote.SchoolApiService.Companion.NYC_SCHOOLS_URL
import com.example.nycschools.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(NYC_SCHOOLS_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideSchoolApiService(retrofit: Retrofit): SchoolApiService =
        retrofit.create(SchoolApiService::class.java)


    @Provides
    @Singleton
    fun provideDatabase(app: Application): SchoolDatabase =
        Room.databaseBuilder(app, SchoolDatabase::class.java, "schoollist.database").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper = NetworkHelper(context)
}