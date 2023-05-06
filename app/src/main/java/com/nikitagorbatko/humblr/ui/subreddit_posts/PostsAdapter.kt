package com.nikitagorbatko.humblr.ui.subreddit_posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitagorbatko.humblr.api.dto.post.ChildPostDTO
import com.nikitagorbatko.humblr.api.dto.subreddit.ChildSubredditDTO
import com.nikitagorbatko.humblr.databinding.PostItemBinding
import com.nikitagorbatko.humblr.databinding.SubredditItemBinding

class PostsAdapter(
    private val onItemClick: (id: String) -> Unit
) :
    PagingDataAdapter<ChildPostDTO, PostsAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)

        //post.data.

        with(holder.binding) {
            root.setOnClickListener {
                post?.data?.id?.let { it1 -> onItemClick(it1) }
            }
            textViewTitle.text = post?.data?.title
            textViewName.text = post?.data?.author
            textViewComments.text = post?.data?.num_comments.toString()
            Glide.with(root)
                .load(post?.data?.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(imageViewMain)
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<ChildPostDTO>() {

    override fun areItemsTheSame(oldItem: ChildPostDTO, newItem: ChildPostDTO): Boolean {
        return oldItem.data.id == oldItem.data.id
    }

    override fun areContentsTheSame(
        oldItem: ChildPostDTO,
        newItem: ChildPostDTO
    ): Boolean {
        return oldItem.data.body == newItem.data.body
    }
}