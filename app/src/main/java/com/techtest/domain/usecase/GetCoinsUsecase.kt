package com.techtest.domain.usecase

import com.techtest.domain.model.Coin
import com.techtest.utils.Result
import kotlinx.coroutines.flow.Flow

interface GetCoinsUsecase {
    suspend operator fun invoke(force: Boolean): Flow<Result<List<Coin>>>
}