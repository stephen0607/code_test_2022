package com.stephennn.codetest01.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stephennn.codetest01.App
import com.stephennn.codetest01.model.CurrencyInfo
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.reflect.Type


enum class SortMode {
    SORT_NAME_ALPHABETICALLY,
    SORT_NAME_LENGTH
}

class CurrencyViewModel : ViewModel() {
    private var sortingJob: Job? = null

    private val _sortMode = MutableLiveData(SortMode.SORT_NAME_ALPHABETICALLY)
    val sortMode: LiveData<SortMode> = _sortMode
    private val _currencyDataList = MutableLiveData<List<CurrencyInfo>>()
    val currencyDataList: LiveData<List<CurrencyInfo>> = _currencyDataList


    fun getDataList() {
        /** collect data from local db as flow **/
        viewModelScope.launch {
            App.instance.dao.fetchAllCurrency().collect {
                _currencyDataList.value = it
            }
        }
        /** observe as viewmodel  **/
//        App.instance.dao.fetchAllCurrency().observeForever {
//        it?.let {_currencyDataList.value = it}
//        }
    }


    fun setFakeData() {
        /** setup fake data list for testing **/
        val collectionType: Type = object : TypeToken<Collection<CurrencyInfo>>() {}.type
        val tempDataList: Collection<CurrencyInfo> =
            Gson().fromJson(App.fakeData, collectionType)
        _currencyDataList.value = tempDataList.toList()
    }

    fun sortingJob() {
        /** invoke sorting job
         *  There are two sorting mode
         *  1) sort by name alphabetically
         *  2) sort by name length
         *  Will switch to another sorting mode when one sorting job is done
         * **/

        _currencyDataList.value?.let {
            if (sortingJob != null) {
                sortingJob?.let {
                    /** cancel the current active coroutine job to prevent concurrency issue **/
                    if (it.isActive) it.cancel()
                }
            }
            sortingJob = viewModelScope.launch {
                /** simulate long time sorting job **/
                delay(1000)
                when (sortMode.value) {
                    SortMode.SORT_NAME_ALPHABETICALLY -> {
                        _currencyDataList.postValue(_currencyDataList.value?.sortedBy { it.name })
                        _sortMode.value = SortMode.SORT_NAME_LENGTH
                    }
                    SortMode.SORT_NAME_LENGTH -> {
                        _currencyDataList.postValue(_currencyDataList.value?.sortedBy { it.name.length })
                        _sortMode.value = SortMode.SORT_NAME_ALPHABETICALLY
                    }
                    else -> {}
                }
            }
        }
    }
}
