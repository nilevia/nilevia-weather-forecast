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

class CityViewModel(
    private val usecase: WeatherForecastUsecase
): ViewModel(){

    private val _favouriteCity = MutableLiveData<List<City>>()
    val favouriteCity: LiveData<List<City>>
        get() = _favouriteCity

    private val _searchedCity = MutableLiveData<List<City>>()
    val searchedCity: LiveData<List<City>>
        get() = _searchedCity


    fun getFavouriteCity(){
        viewModelScope.launch(Dispatchers.IO) {
            usecase.getFavoriteCity().collect{resource ->
                if (resource.status == Resource.Status.SUCCESS){
                    _favouriteCity.postValue(resource.data)
                }
            }
        }
    }


    fun searchCity(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            usecase.searchCity(query).collect{resource ->
                when(resource.status){
                    Resource.Status.LOADING -> {}
                    Resource.Status.SUCCESS -> _searchedCity.postValue(resource.data)
                    Resource.Status.ERROR -> _searchedCity.postValue(null)
                }

            }
        }
    }

}
