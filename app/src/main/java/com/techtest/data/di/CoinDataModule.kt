package com.techtest.data.di

import android.app.Application
import com.techtest.data.local.CoinDao
import com.techtest.data.local.database.CoinDatabase
import com.techtest.data.local.database.CoinDatabaseFactory
import com.techtest.data.local.CoinLocalDataSource
import com.techtest.data.local.CoinLocalDataSourceImpl
import com.techtest.data.mapper.CoinModelMapper
import com.techtest.data.mapper.CoinModelMapperImpl
import com.techtest.data.network.CoinApi
import com.techtest.data.network.CoinNetworkDataSource
import com.techtest.data.network.CoinNetworkDataSourceImpl
import com.techtest.data.repository.CoinRepositoryImpl
import com.techtest.domain.repository.CoinRepository
import com.techtest.utils.AppCoroutineDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinDataModule {
    @Provides
    @Singleton
    fun provideCoinApi(
        retrofit: Retrofit
    ): CoinApi {
        return retrofit.create(CoinApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinDatabase(
        application: Application
    ): CoinDatabase {
        return CoinDatabaseFactory.getDatabase(application)
    }

    @Provides
    @Singleton
    fun provideCoinDao(database: CoinDatabase): CoinDao {
        return database.coinDao()
    }

    @Provides
    @Singleton
    fun provideCoinLocalDataSource(dao: CoinDao): CoinLocalDataSourceImpl {
        return CoinLocalDataSourceImpl(dao = dao)
    }

    @Provides
    @Singleton
    fun provideCoinNetworkDataSource(
        coinApi: CoinApi
    ): CoinNetworkDataSourceImpl {
        return CoinNetworkDataSourceImpl(api = coinApi)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        network: CoinNetworkDataSource,
        local: CoinLocalDataSource,
        dispatchers: AppCoroutineDispatchers,
        mapper: CoinModelMapper
    ): CoinRepositoryImpl {
        return CoinRepositoryImpl(
            network = network,
            local = local,
            dispatchers = dispatchers,
            mapper = mapper
        )
    }

    @Provides
    @Singleton
    fun provideCoinModelMapper(): CoinModelMapperImpl {
        return CoinModelMapperImpl()
    }
}