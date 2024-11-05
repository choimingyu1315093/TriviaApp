package com.example.triviaapp.di

import com.example.triviaapp.network.QuestionApi
import com.example.triviaapp.repository.QuestionRepository
import com.example.triviaapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) //AppModule에서 제공하는 의존성이 애플리케이션의 생명 주기 동안 유지되는 SingletonComponent에 설치됨을 나타냄
@Module //AppModule이 Hilt에 의존성을 제공하는 모듈임을 나타냄
object AppModule {

    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi) = QuestionRepository(api)


    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}