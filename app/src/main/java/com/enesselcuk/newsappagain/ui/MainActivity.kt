package com.enesselcuk.newsappagain.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.enesselcuk.newsappagain.R
import com.enesselcuk.newsappagain.databinding.ActivityMainBinding
import com.enesselcuk.newsappagain.remote.service.db.ArticleDatabase
import com.enesselcuk.newsappagain.repository.NewsRepository
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModel
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
   private lateinit var viewModels: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        run()
    }

    private fun run(){
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.savedNewsFragment, R.id.searchNewsFragment, R.id.breakNewsFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val newsRepository = NewsRepository(ArticleDatabase.createDatabase(this))
        val viewModelFactory = NewsViewModelFactory(newsRepository)
        viewModels = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

    }
}