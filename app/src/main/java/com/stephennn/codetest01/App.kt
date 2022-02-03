package com.stephennn.codetest01

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stephennn.codetest01.model.CurrencyInfo
import com.stephennn.codetest01.room.AppDatabase
import com.stephennn.codetest01.room.CurrencyDAO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class App : Application() {
    lateinit var dao: CurrencyDAO

    companion object {
        lateinit var instance: App
            private set

        const val fakeData = """
        [
        {
        "id": "BTC",
        "name": "Bitcoin",
        "symbol": "BTC"
        },
        {
        "id": "ETH",
        "name": "Ethereum",
        "symbol": "ETH"
        },
        {
        "id": "XRP",
        "name": "XRP",
        "symbol": "XRP"
        },
        {
        "id": "BCH",
        "name": "Bitcoin Cash",
        "symbol": "BCH"
        },
        {
        "id": "LTC",
        "name": "Litecoin",
        "symbol": "LTC"
        },
        {
        "id": "EOS",
        "name": "EOS",
        "symbol": "EOS"
        },
        {
        "id": "BNB",
        "name": "Binance Coin",
        "symbol": "BNB"
        },
        {
        "id": "LINK",
        "name": "Chainlink",
        "symbol": "LINK"
        },
        {
        "id": "NEO",
        "name": "NEO",
        "symbol": "NEO"
        },
        {
        "id": "ETC",
        "name": "Ethereum Classic",
        "symbol": "ETC"
        },
        {
        "id": "ONT",
        "name": "Ontology",
        "symbol": "ONT"
        },
        {
        "id": "CRO",
        "name": "Crypto.com Chain",
        "symbol": "CRO"
        },
        {
        "id": "CUC",
        "name": "Cucumber",
        "symbol": "CUC"
        },
        {
        "id": "USDC",
        "name": "USD Coin",
        "symbol": "USDC"
        }
        ]
    """

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDB()
    }


    private fun initDB() {
        dao = AppDatabase.getDatabase(this).currencyDAO()
        val collectionType: Type = object : TypeToken<Collection<CurrencyInfo>>() {}.type
        val tempDataList: Collection<CurrencyInfo> =
            Gson().fromJson(fakeData, collectionType)
        /** init db and add demo currency list to db**/
        MainScope().launch {
            dao.insertCurrencyList(tempDataList.toList())
        }
    }
}
