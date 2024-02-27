package br.com.euvickson.jetweatherforecast.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.euvickson.jetweatherforecast.model.Unit
import br.com.euvickson.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {

    private val _unitList =  MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged().collect() {listOfUnits ->
                if (listOfUnits.isNullOrEmpty()) {
                    Log.d("TAG", "Empty List: ")
                } else {
                    _unitList.value = listOfUnits
                }
            }
        }
    }

    fun insertFavorite(unit: Unit) = viewModelScope.launch { repository.updateUnit(unit) }
    fun updateFavorite(unit: Unit) = viewModelScope.launch { repository.updateUnit(unit) }
    fun deleteFavorite(unit: Unit) = viewModelScope.launch { repository.deleteUnit(unit) }
    fun deleteAllFavorites() = viewModelScope.launch { repository.deleteAllUnits() }
}