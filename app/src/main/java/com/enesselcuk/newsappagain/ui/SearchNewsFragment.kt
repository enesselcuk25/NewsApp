package com.enesselcuk.newsappagain.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesselcuk.newsappagain.R
import com.enesselcuk.newsappagain.adapter.RcViewAdapter
import com.enesselcuk.newsappagain.adapter.viewHolder.OnClick
import com.enesselcuk.newsappagain.databinding.FragmentSearchNewsBinding
import com.enesselcuk.newsappagain.model.Article
import com.enesselcuk.newsappagain.remote.service.db.ArticleDatabase
import com.enesselcuk.newsappagain.repository.NewsRepository
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModel
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModelFactory
import com.enesselcuk.newsappagain.util.Constans.Companion.SEARCHE_NEWS_DELAY
import com.enesselcuk.newsappagain.util.Resourcess
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception


class SearchNewsFragment : Fragment() {
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

   private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: RcViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
          //  viewModel = (activity as MainActivity).viewModels
            val newRepostiry = NewsRepository(ArticleDatabase.createDatabase(requireContext()))
            val viewFactory = NewsViewModelFactory(newRepostiry)
            viewModel = ViewModelProvider(this,viewFactory)[NewsViewModel::class.java]

            rcAdapter()
            observer()
            search()

        }catch (ex:Exception){

        }
    }

    private fun search(){
        var job:Job? = null

        binding.etSearch.addTextChangedListener { editTable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCHE_NEWS_DELAY)
                editTable?.let {
                    if(editTable.toString().isNotEmpty()){
                        viewModel.searchNews(editTable.toString())
                    }
                }
            }
        }
    }

    private fun observer(){
        viewModel.searchNews.observe(viewLifecycleOwner,  { response ->
            when(response){
                is Resourcess.Success ->{
                    hideSearchPrograssBar()
                    response.data?.let { newResponse ->
                        newsAdapter.differ.submitList(newResponse.articles)
                    }
                }
                is Resourcess.Loading -> {
                    showSearchPrograssBar()
                }
                is Resourcess.Error ->{
                    hideSearchPrograssBar()
                    response.message?.let {  message ->
                        Toast.makeText(requireContext(), "bir hata oluştu: $message", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    private fun rcAdapter(){
        newsAdapter = RcViewAdapter(requireContext(),object : OnClick {
            override fun onCLick(article: Article) {
                val bundle = Bundle().apply {
                    putSerializable("article",article)
                }
                findNavController().navigate(
                    R.id.action_searchNewsFragment_to_articleFragment,
                    bundle
                )
            }
        })

        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

    private fun hideSearchPrograssBar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE

    }

    private fun showSearchPrograssBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}