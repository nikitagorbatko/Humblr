package com.nikitagorbatko.humblr.ui.user

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.databinding.FragmentUserBinding
import com.nikitagorbatko.humblr.ui.CommonLoadStateAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val args: UserFragmentArgs by navArgs()
    private val viewModel: UserViewModel by viewModel()
    private lateinit var adapter: UserCommentsAdapter

    private var isUserFriend: Boolean = false
    private var subscribedColor: Int = -1
    private var notSubscribedColor: Int = -1
    private lateinit var subscribedImage: Drawable
    private lateinit var addPersonImage: Drawable
    private lateinit var subscribeTitle: String
    private lateinit var unsubscribeTitle: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        subscribedColor = context.resources.getColor(R.color.teal_200)
        notSubscribedColor = context.resources.getColor(R.color.main)
        subscribedImage = context.resources.getDrawable(R.drawable.ic_added_person_white)
        addPersonImage = context.resources.getDrawable(R.drawable.ic_add_person_white)
        subscribeTitle = context.resources.getString(R.string.subscribe_title)
        unsubscribeTitle = context.resources.getString(R.string.unsubscribe_title)

        bind()
        observe()
    }

    private fun bind() {
        adapter = UserCommentsAdapter {

        }
        binding.recyclerViewUserComments.adapter = adapter.withLoadStateFooter(
            CommonLoadStateAdapter()
        )
        binding.subscribeCard.setOnClickListener {
            if (isUserFriend) {
                //send request
                binding.subscribeLayout.setBackgroundColor(notSubscribedColor)
                binding.imageViewSubscribeStatus.setImageDrawable(addPersonImage)
                binding.textViewSubscribe.text = subscribeTitle
            } else {
                //send request
                binding.subscribeLayout.setBackgroundColor(subscribedColor)
                binding.imageViewSubscribeStatus.setImageDrawable(subscribedImage)
                binding.textViewSubscribe.text = unsubscribeTitle
            }
            viewModel.viewModelScope.launch {
                viewModel.addAsFriend(args.name)
            }
            isUserFriend = !isUserFriend
        }
    }


    private fun observe() {
        viewModel.viewModelScope.launch {
            viewModel.getUserInfo(args.name)
            viewModel.user.collect {
                if (it != null) {
                    binding.toolbarUser.title = it.data.name
                    isUserFriend = it.data.isFriend == true
                    Glide.with(binding.root)
                        .load(it.data.icon_img)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .error(R.drawable.ic_avatar_frog)
                        .into(binding.imageViewAvatar)
                    binding.textViewUserName.text = it.data.name
                    if (isUserFriend) {
                        binding.subscribeLayout.setBackgroundColor(subscribedColor)
                        binding.imageViewSubscribeStatus.setImageDrawable(subscribedImage)
                        binding.textViewSubscribe.text = unsubscribeTitle
                    } else {
                        binding.subscribeLayout.setBackgroundColor(notSubscribedColor)
                        binding.imageViewSubscribeStatus.setImageDrawable(addPersonImage)
                        binding.textViewSubscribe.text = subscribeTitle
                    }
                }
            }
        }

        viewModel.getUserComments(args.name).onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                when (it) {
                    UserViewModel.State.PRESENT -> {
                        binding.cardViewUserInfo.visibility = View.VISIBLE
                        binding.subscribeCard.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.textViewError.visibility = View.GONE
                    }
                    UserViewModel.State.ERROR -> {
                        binding.cardViewUserInfo.visibility = View.GONE
                        binding.subscribeCard.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.textViewError.visibility = View.VISIBLE
                    }
                    UserViewModel.State.LOADING -> {
                        binding.cardViewUserInfo.visibility = View.GONE
                        binding.subscribeCard.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                        binding.textViewError.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}