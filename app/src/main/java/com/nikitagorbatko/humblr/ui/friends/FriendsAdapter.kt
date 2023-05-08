package com.nikitagorbatko.humblr.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikitagorbatko.humblr.api.dto.friends.FriendDTO
import com.nikitagorbatko.humblr.databinding.FriendItemBinding


class FriendsAdapter(
    //usecase
    private val onItemClick: (name: String) -> Unit
) :
    RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {
    private val friendsList = mutableListOf<FriendDTO>()

    inner class ViewHolder(val binding: FriendItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FriendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = friendsList.size

    fun addAll(friends: List<FriendDTO>) {
        friendsList.addAll(friends)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendsList[position]

        with(holder.binding) {
            textViewPersonName.text = friend.name
            root.setOnClickListener {
               onItemClick(friend.name)
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
}