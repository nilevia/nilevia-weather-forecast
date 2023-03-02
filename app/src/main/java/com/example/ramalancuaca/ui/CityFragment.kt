package com.example.ramalancuaca.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.ramalancuaca.R
import com.nilevia.domain.models.City
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityFragment : Fragment() {

    private val viewModel by viewModel<CityViewModel>()

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
        viewModel.getFavouriteCity()
    }

    @Composable
    private fun initView() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))


            var text by remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text(text = getString(R.string.hint_search_city)) },
            )
            showSearchCity(query = text.text)

            Spacer(modifier = Modifier.height(52.dp))

            viewModel.favouriteCity.observeAsState().value.let {
                showFavouriteCity(cities = it)
            }

        }
    }


    @Composable
    private fun showFavouriteCity(cities: List<City>?) {

        if (cities.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(text = getString(R.string.lbl_favorite_city_empty), fontSize = 16.sp)
            }
        } else {
            Text(text = getString(R.string.lbl_favorite_city))
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(cities) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = {
                            navigateToWeatherForecast(it, true)
                        }) {
                        Text(text = it.fullName, textAlign = TextAlign.Center)
                    }
                }
            }
        }

    }

    @Composable
    private fun showSearchCity(query: String) {
        viewModel.searchCity(query)
        if (query != "") {
            viewModel.searchedCity.observeAsState().value.let { cities ->
                if (cities.isNullOrEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Text(text = getString(R.string.lbl_notfound_network))
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        LazyColumn {
                            items(cities) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable {
                                        navigateToWeatherForecast(it,  false)
                                    }) {
                                    Text(it.fullName, fontSize = 16.sp)
                                }
                                Divider()
                            }

                        }

                    }
                }
            }

        }
    }

    fun navigateToWeatherForecast(city: City, isFromFavourite: Boolean){

        findNavController(this).navigate(CityFragmentDirections.openWeatherForecast(
            city
        ))
    }
}