package com.enesselcuk.newsappagain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesselcuk.newsappagain.R
import com.enesselcuk.newsappagain.adapter.RcViewAdapter
import com.enesselcuk.newsappagain.adapter.viewHolder.OnClick
import com.enesselcuk.newsappagain.databinding.FragmentBreakingNewsBinding
import com.enesselcuk.newsappagain.model.Article
import com.enesselcuk.newsappagain.remote.service.db.ArticleDatabase
import com.enesselcuk.newsappagain.repository.NewsRepository
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModel
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModelFactory
import com.enesselcuk.newsappagain.util.Resourcess


class BreakingNewsFragment : Fragment() {
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: RcViewAdapter
    private lateinit var viewModel: NewsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
         //   viewModel = (activity as MainActivity).viewModels
            val newsRepository = NewsRepository(ArticleDatabase.createDatabase(requireContext()))
            val viewModelFactory = NewsViewModelFactory(newsRepository)
             viewModel = ViewModelProvider(this,viewModelFactory)[NewsViewModel::class.java]
            setUpRecyclerView()


        } catch (ex: Exception) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun observer() {

        viewModel.brekingNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resourcess.Success -> {
                    hidePrograsBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resourcess.Error -> {
                    hidePrograsBar()
                    response.message.let { message ->
                        Toast.makeText(
                            requireContext(),
                            "bir hata oluştu: $message",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                is Resourcess.Loading -> {
                    showPrograsBar()
                }
            }
        })
    }

    private fun hidePrograsBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE

    }

    private fun showPrograsBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE

    }

    private fun setUpRecyclerView() {
        newsAdapter = RcViewAdapter(requireContext(), object : OnClick {
            override fun onCLick(article: Article) {
                val bundle = Bundle().apply {
                    putSerializable("article", article)
                }
                findNavController().navigate(
                    R.id.action_breakingNewsFragment_to_articleFragment,
                    bundle

                )
            }
        })

        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
        observer()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}