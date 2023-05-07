package com.nikitagorbatko.humblr.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.databinding.FragmentAccountBinding
import com.nikitagorbatko.humblr.ui.user.UserViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewModelScope.launch {
            viewModel.getUserInfo()
            viewModel.account.collect {
                if (it != null) {
                    Glide.with(binding.root)
                        .load(it.icon_img)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .error(R.drawable.ic_avatar_frog)
                        .into(binding.imageViewAvatar)
                    binding.textViewAccountName.text = it.name
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                when (it) {
                    AccountViewModel.State.PRESENT -> {
                        binding.cardViewInfo.visibility = View.VISIBLE
                        binding.buttonFriends.visibility = View.VISIBLE
                        binding.buttonLogout.visibility = View.VISIBLE
                        binding.buttonClearSaved.visibility = View.VISIBLE
                        binding.progressBarAccount.visibility = View.GONE
                        binding.textVewAccountError.visibility = View.GONE
                    }
                    AccountViewModel.State.ERROR -> {
                        binding.cardViewInfo.visibility = View.GONE
                        binding.buttonFriends.visibility = View.GONE
                        binding.buttonLogout.visibility = View.GONE
                        binding.buttonClearSaved.visibility = View.GONE
                        binding.progressBarAccount.visibility = View.GONE
                        binding.textVewAccountError.visibility = View.VISIBLE
                    }
                    AccountViewModel.State.LOADING -> {
                        binding.cardViewInfo.visibility = View.GONE
                        binding.buttonFriends.visibility = View.GONE
                        binding.buttonLogout.visibility = View.GONE
                        binding.buttonClearSaved.visibility = View.GONE
                        binding.progressBarAccount.visibility = View.VISIBLE
                        binding.textVewAccountError.visibility = View.GONE
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