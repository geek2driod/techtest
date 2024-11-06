package com.techtest.domain.usecase

import com.techtest.domain.model.Coin
import com.techtest.utils.Result
import kotlinx.coroutines.flow.Flow

interface GetCoinUsecase {
    suspend operator fun invoke(id: String, force: Boolean): Flow<Result<Coin>>
}