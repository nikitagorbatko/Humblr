package com.nikitagorbatko.humblr.ui.subreddits

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.api.pojos.ChildSubredditDto
import com.nikitagorbatko.humblr.api.pojos.SubredditDto
import com.nikitagorbatko.humblr.databinding.SubredditItemBinding

class SubredditsAdapter(
    context: Context,
    private val onItemClick: (subreddit: SubredditDto) -> Unit,
    private val onAddClick: (subscribed: Boolean, name: String) -> Unit
) :
    PagingDataAdapter<ChildSubredditDto, SubredditsAdapter.ViewHolder>(DiffUtilCallback()) {
    private val subscribedImage = context.resources.getDrawable(R.drawable.ic_added_person)
    private val addPersonImage = context.resources.getDrawable(R.drawable.ic_add_person)
    private var subscribedBackground: Drawable
    private var unsubscribedBackground: Drawable

    private val nightModeFlags =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

    init {
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                subscribedBackground =
                    context.resources.getDrawable(R.drawable.text_background_subscribed_night)
                unsubscribedBackground =
                    context.resources.getDrawable(R.drawable.text_background_night)
            }
            else -> {
                subscribedBackground =
                    context.resources.getDrawable(R.drawable.text_background_subscribed)
                unsubscribedBackground = context.resources.getDrawable(R.drawable.text_background)
            }
        }
    }


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
                subreddit?.data?.displayName?.let { onItemClick(subreddit.data) }
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
//            Glide.with(root)
//                .load(subreddit?.data?.url)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .centerCrop()
//                .into(imageViewMain)
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