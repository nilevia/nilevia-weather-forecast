package com.example.ramalancuaca.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.example.ramalancuaca.R
import com.nilevia.domain.models.Weather
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherForecastFragment : Fragment() {

    private val viewModel by viewModel<WeatherViewModel>()

    private val args by navArgs<WeatherForecastFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                initView()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWeatherForecast(args.selectedCity)
    }


    @Composable
    fun initView() {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = args.selectedCity.fullName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            //Spacer(modifier = Modifier.fi)
            viewModel.isCitySaved.observeAsState().value.let {
                if (it == null || it == false ) {
                    Button(onClick = { viewModel.saveFavourite(args.selectedCity) }) {
                        Text(text = getString(R.string.btn_save_city))
                    }
                } else {
                    Button(onClick = { viewModel.removeFavourite(args.selectedCity) }) {
                        Text(text = getString(R.string.btn_delete_city))
                    }
                }
            }

            Spacer(modifier = Modifier.height(42.dp))

            viewModel.weatherLayoutState.observeAsState().value?.let {
                when (it.first) {
                    WeatherViewModel.Companion.LayoutState.LAYOUT_LOADING -> ShowLoading()
                    WeatherViewModel.Companion.LayoutState.LAYOUT_SUCCESS -> ShowWeather(it.second)
                    WeatherViewModel.Companion.LayoutState.LAYOUT_ERROR -> ShowError()
                }
            }


        }

    }

    @Composable
    fun ShowWeather(listWeather: List<Weather>?) {
        if (listWeather.isNullOrEmpty()) {
            ShowError()
        } else {
            LazyColumn {
                items(listWeather) { weather ->
                    Column {
                        Row {
                            Text(
                                text = "${weather.date}  ${weather.status}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = getString(R.string.lbl_temperature), fontSize = 12.sp)
                            Text(text = getString(R.string.lbl_humidity), fontSize = 12.sp)
                            Text(text = getString(R.string.lbl_wind), fontSize = 12.sp)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "${weather.temperatureMin} - ${weather.temperatureMax}",
                                fontSize = 14.sp
                            )
                            Text(text = weather.humidity, fontSize = 14.sp)
                            Text(text = weather.wind, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }

    }

    @Composable
    fun ShowLoading() {

        CircularProgressIndicator()
    }

    @Composable
    fun ShowError() {

        Text(text = getString(R.string.lbl_retry_network))
        Button(onClick = { viewModel.getWeatherForecast(args.selectedCity) }) {
            Text(text = getString(R.string.btn_retry_network))
        }
    }
}