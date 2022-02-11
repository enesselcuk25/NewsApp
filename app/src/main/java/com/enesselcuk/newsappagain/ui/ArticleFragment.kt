package com.enesselcuk.newsappagain.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.enesselcuk.newsappagain.databinding.FragmentArticleBinding
import com.enesselcuk.newsappagain.remote.service.db.ArticleDatabase
import com.enesselcuk.newsappagain.repository.NewsRepository
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModel
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar


class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel:NewsViewModel
    private val args :ArticleFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
         //   viewModel = (activity as MainActivity).viewModels
             val newRepostiry = NewsRepository(ArticleDatabase.createDatabase(requireContext()))
            val viewFactory = NewsViewModelFactory(newRepostiry)
            viewModel = ViewModelProvider(this,viewFactory)[NewsViewModel::class.java]

            val article =args.article

            binding.webView.apply {
                webViewClient = WebViewClient()
                article.url.let { loadUrl(it) }
            }

            binding.fab.setOnClickListener {
                viewModel.saveArticles(article)
                Snackbar.make(view,"kayıt edildi",Snackbar.LENGTH_SHORT).show()
            }

        }catch (ex:Exception){

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}