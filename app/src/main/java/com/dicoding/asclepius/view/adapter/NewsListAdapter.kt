package com.dicoding.asclepius.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.ArticlesItem
import com.dicoding.asclepius.databinding.NewsItemBinding

class NewsListAdapter(private val listArticle: List<ArticlesItem?>, val newsListener: NewsListener): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root) {


        fun bind(article: ArticlesItem){
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            article.urlToImage?.let {
                binding.imgNews.load(it) {
                    placeholder(R.drawable.ic_place_holder)
                    transformations(RoundedCornersTransformation(24f))
                }
            }
            binding.root.setOnClickListener {
                newsListener.onClick(Uri.parse(article.url))
            }



        }
    }

    interface NewsListener {
        fun onClick(uri: Uri)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = listArticle.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        listArticle[position]?.let { holder.bind(it) }
    }
}