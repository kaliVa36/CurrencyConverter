package com.currencyconverter.app.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyconverter.app.data.datasource.ApiDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(private val apiDataSource: ApiDataSource): ViewModel() {
    init {
        viewModelScope.launch {
            apiDataSource.getCurrencies()
        }
    }
}
