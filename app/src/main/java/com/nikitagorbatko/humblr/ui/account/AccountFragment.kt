package com.nikitagorbatko.humblr.ui.account

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
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

    private lateinit var alertDialog: AlertDialog

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

        binding.buttonLogout.setOnClickListener {
            showLogoutDialog()
        }

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

                    binding.buttonFriends.setOnClickListener {
                        val action =
                            AccountFragmentDirections.actionNavigationUserToFriendsFragment()
                        findNavController().navigate(action)
                    }
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

    private fun showLogoutDialog() {
        val activity = requireActivity()

        alertDialog = activity.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok
                ) { _, _ ->
                    viewModel.logout()
                    activity.finish()
                }
                setNegativeButton(R.string.cancel
                ) { _, _ ->
                    alertDialog.cancel()
                }
            }
            builder.setTitle(R.string.logout_warning_message)
            builder.create()
        }

        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}