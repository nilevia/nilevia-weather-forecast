package com.example.ramalancuaca.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nilevia.domain.models.City
import com.nilevia.domain.models.Weather
import com.nilevia.domain.usecases.WeatherForecastUsecase
import com.nilevia.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val usecase: WeatherForecastUsecase
): ViewModel(){

    private val _weatherLayoutState = MutableLiveData<Pair<LayoutState, List<Weather>?>>()
    val weatherLayoutState: LiveData<Pair<LayoutState, List<Weather>?>>
        get() = _weatherLayoutState

    private val _isCitySaved = MutableLiveData<Boolean>()
    val isCitySaved: LiveData<Boolean>
        get() = _isCitySaved


    fun saveFavourite(selectedCity: City){
        viewModelScope.launch(Dispatchers.IO) {
            val currentTimeAsId = System.currentTimeMillis().toString()

            usecase.saveFavoriteCity(selectedCity, currentTimeAsId).collect{resource ->
                if (resource.status == Resource.Status.SUCCESS){
                    _isCitySaved.postValue(true)
                }
            }
        }
    }

    fun removeFavourite(selectedCity: City){
        viewModelScope.launch(Dispatchers.IO) {
            usecase.deleteFavoriteCity(selectedCity).collect{resource ->
                if (resource.status == Resource.Status.SUCCESS){
                    _isCitySaved.postValue(false)
                }
            }
        }
    }



    fun getWeatherForecast(selectedCity : City){
        viewModelScope.launch(Dispatchers.IO) {
            usecase.getWeatherForecast(selectedCity).collect{
                when(it.status){
                    Resource.Status.LOADING -> _weatherLayoutState.postValue(Pair(LayoutState.LAYOUT_LOADING, null))
                    Resource.Status.SUCCESS -> _weatherLayoutState.postValue(Pair(LayoutState.LAYOUT_SUCCESS, it.data))
                    Resource.Status.ERROR -> _weatherLayoutState.postValue(Pair(LayoutState.LAYOUT_ERROR, null))
                }
            }
        }
    }


    companion object{
        enum class LayoutState {
            LAYOUT_LOADING,
            LAYOUT_SUCCESS,
            LAYOUT_ERROR
        }
    }

}
