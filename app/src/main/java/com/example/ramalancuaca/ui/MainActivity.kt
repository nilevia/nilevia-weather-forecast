package com.example.ramalancuaca.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.example.ramalancuaca.R
import com.example.ramalancuaca.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostActivityMain) as NavHostFragment
        var graph: NavGraph =
            navHostFragment.navController.navInflater.inflate(R.navigation.nav_graph_main)

        navHostFragment.navController.setGraph(graph, null)

    }

}