package com.nikitagorbatko.humblr.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.databinding.TestCommentItemBinding

class CommentsAdapter(
    private val onItemClick: (author: String) -> Unit
) : PagingDataAdapter<CommentDto, CommentsAdapter.ViewHolder>(DiffUtilCallback()) {

    inner class ViewHolder(val binding: TestCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TestCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getItem(position)

        //post.data.

        with(holder.binding) {
            textView.text = comment?.data?.body + "\n\n"
            root.setOnClickListener {
                comment?.data?.author?.let { it1 -> onItemClick(it1) }
            }
//            root.setOnClickListener {
//                post?.data?.id?.let { it1 -> onItemClick(it1) }
//            }
//            textViewTitle.text = post?.data?.title
//            textViewName.text = post?.data?.author
//            textViewComments.text = post?.data?.num_comments.toString()
//            Glide.with(root)
//                .load(post?.data?.url)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .centerCrop()
//                .into(imageViewMain)
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<CommentDto>() {

        override fun areItemsTheSame(oldItem: CommentDto, newItem: CommentDto): Boolean {
            return oldItem.data?.id == oldItem.data?.id
        }

        override fun areContentsTheSame(
            oldItem: CommentDto,
            newItem: CommentDto
        ): Boolean {
            return oldItem.data?.numComments == newItem.data?.numComments
        }
    }
}