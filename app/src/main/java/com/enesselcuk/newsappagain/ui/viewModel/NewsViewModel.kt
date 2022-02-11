package com.enesselcuk.newsappagain.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesselcuk.newsappagain.model.Article
import com.enesselcuk.newsappagain.model.NewsResponse
import com.enesselcuk.newsappagain.repository.NewsRepository
import com.enesselcuk.newsappagain.util.Resourcess
import kotlinx.coroutines.launch
import retrofit2.Response



class NewsViewModel(
   private val newsRepository: NewsRepository,
    ):ViewModel() {

      val brekingNews:MutableLiveData<Resourcess<NewsResponse>> = MutableLiveData()
    private var brekingNewsPage =1


    val searchNews:MutableLiveData<Resourcess<NewsResponse>> = MutableLiveData()
    private var searchNewsPage = 1


    init {
        getBreakingNews("tr")

    }


    fun getBreakingNews(countryCode:String){
        viewModelScope.launch {
            brekingNews.postValue(Resourcess.Loading())
            val response = newsRepository.getBreakingNews(countryCode,brekingNewsPage)
            brekingNews.postValue(handleBreakNewsResponse(response))
        }
    }
    fun searchNews(searchQuery:String) =
        viewModelScope.launch {
            searchNews.postValue(Resourcess.Loading())
            val response = newsRepository.searchNews(searchQuery,searchNewsPage)
            searchNews.postValue(handleSearchNewsResponse(response))
        }


     fun saveArticles(article: Article) =
        viewModelScope.launch {
            newsRepository.upsert(article)
        }

     fun getSavedNews() = newsRepository.getSaveNews()

     fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>):Resourcess<NewsResponse>{

        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resourcess.Success(resultResponse)
            }
        }
        return Resourcess.Error(response.message())
    }


    private fun handleBreakNewsResponse(response: Response<NewsResponse>):Resourcess<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resourcess.Success(resultResponse)
            }
        }
        return  Resourcess.Error(response.message())
    }
}