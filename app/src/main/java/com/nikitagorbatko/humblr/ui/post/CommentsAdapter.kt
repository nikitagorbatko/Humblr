package com.nikitagorbatko.humblr.ui.post

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.databinding.TestCommentItemBinding
import java.text.SimpleDateFormat
import java.util.Date


class CommentsAdapter(
    private val onItemClick: (author: String) -> Unit,
    private val onVoteUp: (id: String) -> Unit,
    private val onVoteDown: (id: String) -> Unit,
    private val saveComment: (id: String) -> Unit,
    private val unsaveComment: (id: String) -> Unit
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    private val commentsList = mutableListOf<CommentDto>()

    inner class ViewHolder(val binding: TestCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            TestCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = commentsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(comments: List<CommentDto>) {
        commentsList.addAll(comments)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentsList[position]
        val timeCreated = comment.data?.created?.let { convertLongToTime(it) }
        val saved = comment.data?.saved == true

        with(holder.binding) {
            textName.text = comment.data?.author
            textBody.text = comment.data?.body
            textTime.text = timeCreated ?: "-:-"
            imageSave.setImageResource(
                if (saved) {
                    R.drawable.ic_favourited
                } else {
                    R.drawable.ic_favourite
                }
            )

            imageVoteDown.setOnClickListener {
                comment.data?.name?.let { it1 -> onVoteDown(it1) }
            }
            imageVoteUp.setOnClickListener {
                comment.data?.name?.let { it1 -> onVoteUp(it1) }
            }
            imageSave.setOnClickListener {
                if (comment.data?.saved == true) {
                    imageSave.setImageResource(R.drawable.ic_favourite)
                    commentsList[position].data!!.saved = false
                    comment.data!!.name?.let { it1 -> unsaveComment(it1) }
                } else {
                    imageSave.setImageResource(R.drawable.ic_favourited)
                    commentsList[position].data!!.saved = true
                    comment.data!!.name?.let { it1 -> saveComment(it1) }
                }
                notifyItemChanged(position)
            }
            root.setOnClickListener {
                comment.data?.author?.let { it1 -> onItemClick(it1) }
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


    fun convertLongToTime(time: Int): String {
        val date = Date(time.toLong())
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
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