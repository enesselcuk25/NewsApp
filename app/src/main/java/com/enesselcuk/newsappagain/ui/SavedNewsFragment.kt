package com.enesselcuk.newsappagain.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enesselcuk.newsappagain.R
import com.enesselcuk.newsappagain.adapter.RcViewAdapter
import com.enesselcuk.newsappagain.adapter.viewHolder.OnClick
import com.enesselcuk.newsappagain.databinding.FragmentSavedNewsBinding
import com.enesselcuk.newsappagain.model.Article
import com.enesselcuk.newsappagain.remote.service.db.ArticleDatabase
import com.enesselcuk.newsappagain.repository.NewsRepository
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModel
import com.enesselcuk.newsappagain.ui.viewModel.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar


class SavedNewsFragment : Fragment() {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: RcViewAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {


            val newRepostiry = NewsRepository(ArticleDatabase.createDatabase(requireContext()))
            val viewFactory = NewsViewModelFactory(newRepostiry)
            viewModel = ViewModelProvider(this, viewFactory)[NewsViewModel::class.java]
            rcViewAdapter()

        } catch (ex: Exception) {

        }

    }

    private fun rcViewAdapter() {

        newsAdapter = RcViewAdapter(requireContext(), object : OnClick {
            override fun onCLick(article: Article) {
                val bundle = Bundle().apply {
                    putSerializable("article", article)
                }
                findNavController().navigate(
                    R.id.action_savedNewsFragment_to_articleFragment,
                    bundle

                )
            }

        })

        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }


        // add and deleted the kods

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)

                view?.let {
                    Snackbar.make(it, "deleted", Snackbar.LENGTH_SHORT).apply {
                        setAction("undo") {
                            viewModel.saveArticles(article)
                        }
                        show()
                    }
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}