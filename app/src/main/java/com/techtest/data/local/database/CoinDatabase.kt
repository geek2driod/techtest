package com.techtest.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techtest.data.local.CoinDao
import com.techtest.data.local.model.CoinLocalModel

@TypeConverters(CoinDatabaseTypeConvertor::class)
@Database(
    entities = [CoinLocalModel::class],
    version = 1
)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}