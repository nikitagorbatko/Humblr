package com.nikitagorbatko.humblr.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO
import com.nikitagorbatko.humblr.databinding.SubredditItemBinding

class SubredditsAdapter(private val onItemClick: (String) -> Unit) :
    PagingDataAdapter<ChildSubredditDTO, SubredditsAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(val binding: SubredditItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SubredditItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)

        with(holder.binding) {
//            Glide.with(root)
//                .load(review?.multimediaSrc)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .centerCrop()
//                .into(imageViewReview)
//            textViewReviewTitle.text = review?.displayTitle
//            textViewReviewArticle.text = review?.headline
//            textViewAuthor.text = review?.byline
//            textViewData.text = review?.dateUpdated
//            buttonRead.setOnClickListener {
//                review?.linkUrl?.let { it1 -> onItemClick(it1) }
//            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<ChildSubredditDTO>() {

    override fun areItemsTheSame(oldItem: ChildSubredditDTO, newItem: ChildSubredditDTO): Boolean {
        return oldItem.data.subscribed == oldItem.data.subscribed
    }

    override fun areContentsTheSame(oldItem: ChildSubredditDTO, newItem: ChildSubredditDTO): Boolean {
        return oldItem.data.name == newItem.data.name
    }
}