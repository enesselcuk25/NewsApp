package com.enesselcuk.newsappagain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.enesselcuk.newsappagain.adapter.viewHolder.ArticleViewHolder
import com.enesselcuk.newsappagain.adapter.viewHolder.OnClick
import com.enesselcuk.newsappagain.databinding.IteArticlePreviewBinding
import com.enesselcuk.newsappagain.model.Article

class RcViewAdapter(
    private val Context: Context,
    private val onArticelOnCLick:OnClick) :
    RecyclerView.Adapter<ArticleViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder{
        val binding = IteArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return  ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articles = differ.currentList[position]
        with(holder){
                Glide.with(Context)
                    .load(articles.urlToImage)
                    .into(binding.ivArticleImage)

                binding.tvSource.text = articles.source.name
                binding.tvTitle.text = articles.title
                binding.tvDescription.text = articles.description
                binding.tvPublishedAt.text = articles.publishedAt

            this.bind(articles,onArticelOnCLick)

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}