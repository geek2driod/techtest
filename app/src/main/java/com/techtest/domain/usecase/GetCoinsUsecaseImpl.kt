package com.techtest.domain.usecase

import com.techtest.domain.model.Coin
import com.techtest.domain.repository.CoinRepository
import com.techtest.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUsecaseImpl @Inject constructor(
    private val repository: CoinRepository
) : GetCoinsUsecase {
    override suspend fun invoke(force: Boolean): Flow<Result<List<Coin>>> {
        return flow {
            emit(Result.Loading())
            emit(repository.getCoins(force = force, sorted = true))
        }
    }
}