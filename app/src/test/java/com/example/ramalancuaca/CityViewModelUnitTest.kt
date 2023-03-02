package com.example.ramalancuaca

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ramalancuaca.ui.CityViewModel
import com.nilevia.domain.models.City
import com.nilevia.domain.usecases.WeatherForecastUsecase
import com.nilevia.domain.utils.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


class CityViewModelUnitTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var useCase: WeatherForecastUsecase
    lateinit var viewModel: CityViewModel

    private val favouriteCityObserver = mockk<Observer<List<City>>>(relaxed = true)
    private val searchedCityObserver = mockk<Observer<List<City>>>(relaxed = true)

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = CityViewModel(useCase)
        viewModel.searchedCity.observeForever(searchedCityObserver)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks(true)
    }

    @Test
    fun `search City and return list of city`() {
        // mock result of api
        runBlocking {
            val resultLoading = Resource<List<City>>(Resource.Status.LOADING)
            val resultSuccess = Resource<List<City>>(
                Resource.Status.SUCCESS, listOf(
                    City(
                        fullName = "Malang",
                        lat = "1234",
                        lon = "7623"
                    )
                )
            )

            // mock to return loading at first time and then return success
            coEvery {
                useCase.searchCity(any<String>())
            } returns flow {
                emit(resultLoading)
                emit(resultSuccess)
            }

            // triger api call
            viewModel.searchCity("Malang")

            // verify observer change as a result of api call
            verifyOrder {
                searchedCityObserver.onChanged(listOf())
                searchedCityObserver.onChanged(resultSuccess.data)
            }
        }

    }

    @Test
    fun `search City and return error`() {
        // mock result of api
        runBlocking {
            val resultLoading = Resource<List<City>>(Resource.Status.LOADING)
            val resultSuccess = Resource<List<City>>(Resource.Status.ERROR)

            // mock to return loading at first time and then return success
            coEvery {
                useCase.searchCity(any<String>())
            } returns flow {
                emit(resultLoading)
                emit(resultSuccess)
            }

            // triger api call
            viewModel.searchCity("Malang")

            // verify observer change as a result of api call
            verifyOrder {
                searchedCityObserver.onChanged(listOf())
                searchedCityObserver.onChanged(null)
            }
        }

    }


}