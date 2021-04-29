package com.example.spendtrackernotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.spendtrackernotes.data.Spend
import com.example.spendtrackernotes.data.SpendsTrackerDataSource
import java.util.*

class SpendViewModel(
    private val dataSource: SpendsTrackerDataSource
) : ViewModel() {

    private val _last50SpendsLiveData = MutableLiveData<List<Spend>>()
    val last50SpendsLiveData: LiveData<List<Spend>>
        get() = _last50SpendsLiveData

    fun addSpend(amount: Int, description: String) = viewModelScope.launch {
        dataSource.addSpend(Spend(Date(), amount, description))
    }

    fun getLast50Spends() = viewModelScope.launch {
        _last50SpendsLiveData.value = dataSource.getLast50Spends()
    }
}