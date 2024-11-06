package com.techtest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techtest.data.local.model.CoinLocalModel

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coins: List<CoinLocalModel>)

    @Query("SELECT * FROM CoinLocalModel")
    suspend fun getCoins(): List<CoinLocalModel>

    @Query("DELETE FROM CoinLocalModel")
    suspend fun deleteAll()

    @Query("SELECT * FROM CoinLocalModel WHERE id = :id")
    suspend fun getCoinById(id: String): CoinLocalModel?
}