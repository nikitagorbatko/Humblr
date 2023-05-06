package com.nikitagorbatko.humblr.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikitagorbatko.humblr.api.dto.CommentDto
import com.nikitagorbatko.humblr.api.dto.CommentsResponse
import com.nikitagorbatko.humblr.databinding.TestCommentItemBinding


class CommentsAdapter(
    private val onItemClick: (id: String) -> Unit
) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    private val commentsList = mutableListOf<CommentDto>()

    inner class ViewHolder(val binding: TestCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TestCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = commentsList.size

    fun addAll(comments: List<CommentDto>) {
        commentsList.addAll(comments)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentsList[position]

        //post.data.

        with(holder.binding) {
            textView.text = comment?.data?.body + "\n\n"
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