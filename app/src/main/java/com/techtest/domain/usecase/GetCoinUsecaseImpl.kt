package com.techtest.domain.usecase

import com.techtest.domain.model.Coin
import com.techtest.domain.repository.CoinRepository
import com.techtest.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinUsecaseImpl @Inject constructor(
    private val repository: CoinRepository
) : GetCoinUsecase {
    override suspend fun invoke(id: String, force: Boolean): Flow<Result<Coin>> {
        return flow {
            emit(Result.Loading())
            emit(repository.getCoin(id, force))
        }
    }
}