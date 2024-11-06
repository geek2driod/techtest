package com.techtest.data.local.database

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.techtest.data.local.model.CoinTagLocalModel
import java.lang.reflect.Type

class CoinDatabaseTypeConvertor {
    private val gson = GsonBuilder().create()
    private val type: Type = object : TypeToken<ArrayList<CoinTagLocalModel>>() {}.type

    @TypeConverter
    fun fromTagListToString(list: ArrayList<CoinTagLocalModel>?): String? {
        if (list == null) return null
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toTagListFromString(list: String?): ArrayList<CoinTagLocalModel>? {
        if (list == null) return null
        return gson.fromJson(list, type)
    }
}