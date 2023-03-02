package com.nilevia.data.mappers

import com.nilevia.data.models.CityDTO
import com.nilevia.domain.models.City

object CityMapper {

    fun mapToCity(serverData: List<CityDTO>): List<City> {

        val listCity = mutableListOf<City>()

        serverData.forEach{ city->
            val name =  city.localName?.id ?: city.name ?: "-"

            listCity.add(City(
                fullName = name,
                lat = city.lat.orEmpty(),
                lon = city.lat.orEmpty()
            ))
        }

        return listCity
    }

    fun mapToCityDTO(localData: City): CityDTO{
        return CityDTO(
            id = localData.id,
            name = localData.fullName,
            lat = localData.lat,
            lon = localData.lon
        )
    }
}