package com.techtest.data.di

import com.techtest.data.local.CoinLocalDataSource
import com.techtest.data.local.CoinLocalDataSourceImpl
import com.techtest.data.mapper.CoinModelMapper
import com.techtest.data.mapper.CoinModelMapperImpl
import com.techtest.data.network.CoinNetworkDataSource
import com.techtest.data.network.CoinNetworkDataSourceImpl
import com.techtest.data.repository.CoinRepositoryImpl
import com.techtest.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoinDataBindModule {
    @Binds
    fun bindCoinLocalDataSource(impl: CoinLocalDataSourceImpl): CoinLocalDataSource

    @Binds
    fun bindCoinNetworkDataSource(impl: CoinNetworkDataSourceImpl): CoinNetworkDataSource

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    @Binds
    fun bindCoinModelMapper(impl: CoinModelMapperImpl): CoinModelMapper
}