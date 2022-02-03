package com.stephennn.codetest01.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stephennn.codetest01.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDAO {
    @Query("SELECT * FROM currencyInfoTable")
    fun fetchAllCurrency(): Flow<List<CurrencyInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(deliveryData: CurrencyInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyList(deliveryDataList: List<CurrencyInfo>)
}