package com.nikitagorbatko.humblr.ui.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import com.nikitagorbatko.humblr.databinding.SubredditItemBinding

class SubredditsAdapter(
    context: Context,
    private val onItemClick: (displayName: String) -> Unit,
    private val onAddClick: (subscribed: Boolean, name: String) -> Unit
) : PagingDataAdapter<ChildSubredditDto, SubredditsAdapter.ViewHolder>(DiffUtilCallback()) {
    private val subscribedImage = context.resources.getDrawable(R.drawable.ic_added_person)
    private val addPersonImage = context.resources.getDrawable(R.drawable.ic_add_person)
    private val subscribedBackground =
        context.resources.getDrawable(R.drawable.text_background_subscribed)
    private val unsubscribedBackground = context.resources.getDrawable(R.drawable.text_background)


    inner class ViewHolder(val binding: SubredditItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SubredditItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subreddit = getItem(position)

        with(holder.binding) {
            root.setOnClickListener {
                subreddit?.data?.displayName?.let { onItemClick(it) }
            }
            textPostTitle.text = subreddit?.data?.title
            val subscribed = subreddit?.data?.subscribed == true
            imageAddPerson.setImageDrawable(
                if (subscribed) {
                    subscribedImage
                } else {
                    addPersonImage
                }
            )
            textPostTitle.background = if (subscribed) {
                subscribedBackground
            } else {
                unsubscribedBackground
            }
            frameClick.setOnClickListener {
                subreddit?.data?.subscribed = !subscribed
                notifyItemChanged(position)
                onAddClick(subscribed, subreddit?.data?.name ?: "")
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<ChildSubredditDto>() {

    override fun areItemsTheSame(oldItem: ChildSubredditDto, newItem: ChildSubredditDto): Boolean {
        return oldItem.data.subscribed == oldItem.data.subscribed
    }

    override fun areContentsTheSame(
        oldItem: ChildSubredditDto,
        newItem: ChildSubredditDto
    ): Boolean {
        return oldItem.data.name == newItem.data.name
    }
}