package com.example.pusatara_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pusatara_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment)
        navController.popBackStack(R.id.menuHome, false)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.menuHome, R.id.menuGlossary, R.id.menuMedia
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        if (intent.hasExtra("fragmentToLoad")) {
            val nav = findNavController(R.id.nav_host_fragment)
            nav.popBackStack(nav.graph.id, false)
            nav.navigate(R.id.menuMedia)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}