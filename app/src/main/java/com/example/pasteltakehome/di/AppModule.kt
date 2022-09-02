package com.example.pasteltakehome.di

import android.content.Context
import androidx.room.Room
import com.example.pasteltakehome.data.local.NewsArticleDao
import com.example.pasteltakehome.data.local.NewsArticleDatabase
import com.example.pasteltakehome.data.remote.NewsApiService
import com.example.pasteltakehome.data.repository.Repository
import com.example.pasteltakehome.data.repository.RepositoryImpl
import com.example.pasteltakehome.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit) : NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsArticleDatabase{
        return Room.databaseBuilder(context, NewsArticleDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseDAO(database: NewsArticleDatabase) : NewsArticleDao = database.newsArticleDao()

    @Provides
    @Singleton
    fun provideNewsArticleRepository(apiService: NewsApiService,
                                     database: NewsArticleDatabase,
    @Dispatcher(ResourceDispatcher.IO) ioDispatcher: CoroutineDispatcher): Repository {
        return RepositoryImpl(apiService, database, ioDispatcher)
    }
}
