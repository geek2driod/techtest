package com.techtest.data.network

import com.techtest.R
import com.techtest.data.network.model.CoinNetworkModel
import com.techtest.utils.Result
import com.techtest.utils.UiMessage
import retrofit2.HttpException
import java.io.IOException

class CoinNetworkDataSourceImpl(
    private val api: CoinApi
) : CoinNetworkDataSource {
    override suspend fun getCoins(): Result<List<CoinNetworkModel>> {
        return try {
            val response = api.getCoins().take(COINS_LIMIT)
            Result.Success(data = response)
        } catch (e: HttpException) {
            Result.Error(message = UiMessage.ResourceType(R.string.network_error))
        } catch (e: IOException) {
            Result.Error(message = UiMessage.ResourceType(R.string.internet_error))
        }
    }

    override suspend fun getCoin(id: String): Result<CoinNetworkModel> {
        return try {
            val response = api.getCoin(id)
            Result.Success(data = response)
        } catch (e: HttpException) {
            Result.Error(message = UiMessage.ResourceType(R.string.network_error))
        } catch (e: IOException) {
            Result.Error(message = UiMessage.ResourceType(R.string.internet_error))
        }
    }

    companion object {
        // NOTE: API limit is 1000 requests per day and returns lots of coins at once
        private const val COINS_LIMIT = 60
    }
}