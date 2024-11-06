package com.techtest.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinLocalModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "symbol")
    val symbol: String = "",

    @ColumnInfo(name = "logo")
    val logo: String = "",

    @ColumnInfo(name = "tags")
    val tags: ArrayList<CoinTagLocalModel>? = null,

    @ColumnInfo(name = "description")
    val description: String = ""
)