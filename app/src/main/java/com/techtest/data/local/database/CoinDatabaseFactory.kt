package com.techtest.data.local.database

import android.app.Application
import androidx.room.Room

object CoinDatabaseFactory {
    private const val DATABASE_NAME = "coin.db"
    fun getDatabase(application: Application): CoinDatabase {
        return Room.databaseBuilder(
            application,
            CoinDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}