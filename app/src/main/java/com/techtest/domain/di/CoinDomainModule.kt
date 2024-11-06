package com.techtest.domain.di

import com.techtest.domain.usecase.GetCoinUsecase
import com.techtest.domain.usecase.GetCoinUsecaseImpl
import com.techtest.domain.usecase.GetCoinsUsecase
import com.techtest.domain.usecase.GetCoinsUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoinDomainModule {
    @Binds
    fun bindGetCoinsUsecase(impl: GetCoinsUsecaseImpl): GetCoinsUsecase

    @Binds
    fun bindGetCoinUsecase(impl: GetCoinUsecaseImpl): GetCoinUsecase
}