package com.nikitagorbatko.humblr.ui.friends

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.api.pojos.FriendDto
import com.nikitagorbatko.humblr.data.friends_photos.FriendsPhotosRepository
import com.nikitagorbatko.humblr.databinding.FriendItemBinding
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent


class FriendsAdapter(
    context: Context,
    private val scope: LifecycleCoroutineScope,
    val onItemClick: (name: String) -> Unit
) :
    RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {
    private val friendsList = mutableListOf<FriendDto>()
    private val repository: FriendsPhotosRepository by KoinJavaComponent.inject(
        FriendsPhotosRepository::class.java
    )

    private var unsubscribedBackground: Drawable

    private val nightModeFlags =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

    init {
        unsubscribedBackground = when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                context.resources.getDrawable(R.drawable.text_background_night)
            }
            else -> {
                context.resources.getDrawable(R.drawable.text_background)
            }
        }
    }

    private val token: String by KoinJavaComponent.inject(String::class.java)

    inner class ViewHolder(val binding: FriendItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FriendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = friendsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(friends: List<FriendDto>) {
        friendsList.addAll(friends)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendsList[position]

        with(holder.binding) {
            textPersonName.text = friend.name
            textPersonName.background = unsubscribedBackground
            root.setOnClickListener {
                onItemClick(friend.name)
            }
            scope.launch {
                try {
                    val glideUrl = GlideUrl(
                        repository.getFriendInfo(friend.name)?.icon_img,
                        LazyHeaders.Builder()
                            .addHeader("Authorization", token)
                            .build()
                    )
                    Glide.with(root)
                        .load(glideUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imagePerson)
                } catch (_: Exception) { }
            }
        }
    }
}