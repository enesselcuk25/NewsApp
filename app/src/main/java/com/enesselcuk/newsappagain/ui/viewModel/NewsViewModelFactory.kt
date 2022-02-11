package com.enesselcuk.newsappagain.ui.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enesselcuk.newsappagain.repository.NewsRepository

class NewsViewModelFactory(
    private val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECK_CAST")
            return NewsViewModel(newsRepository) as T
        }
        throw  IllegalArgumentException("error")

    }
}