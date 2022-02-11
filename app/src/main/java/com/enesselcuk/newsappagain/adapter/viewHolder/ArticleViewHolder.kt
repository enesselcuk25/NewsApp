package com.enesselcuk.newsappagain.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.enesselcuk.newsappagain.databinding.IteArticlePreviewBinding
import com.enesselcuk.newsappagain.model.Article

class ArticleViewHolder(val binding: IteArticlePreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article, onItemClick: OnClick) {
        binding.ivArticleImage.setOnClickListener {
            onItemClick.onCLick(article)
        }
    }


}