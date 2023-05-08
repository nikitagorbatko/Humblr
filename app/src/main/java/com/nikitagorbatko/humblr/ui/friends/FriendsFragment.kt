package com.nikitagorbatko.humblr.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.nikitagorbatko.humblr.databinding.FragmentFriendsBinding
import com.nikitagorbatko.humblr.ui.account.AccountViewModel
import com.nikitagorbatko.humblr.ui.subreddits.SubredditsViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendsFragment : Fragment() {
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FriendsViewModel by viewModel()

    private lateinit var adapter: FriendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind()
        observe()
    }

    private fun bind() {
        val navController = findNavController()
        adapter = FriendsAdapter {
            val action = FriendsFragmentDirections.actionFriendsFragmentToUserFragment(it)
            navController.navigate(action)
        }

        val itemDecoration = SpacesItemDecoration(32)
        binding.recyclerFriends.addItemDecoration(itemDecoration)
        binding.recyclerFriends.adapter = adapter

        binding.toolbarFriends.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    private fun observe() {
        viewModel.viewModelScope.launch {
            viewModel.getFriends()
            viewModel.friends.collect {
                adapter.addAll(it)
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                when (it) {
                    FriendsViewModel.State.PRESENT -> {
                        binding.progressBarFriends.visibility = View.GONE
                        binding.textViewFriendsError.visibility = View.GONE
                        binding.recyclerFriends.visibility = View.VISIBLE
                    }
                    FriendsViewModel.State.ERROR -> {
                        binding.progressBarFriends.visibility = View.GONE
                        binding.textViewFriendsError.visibility = View.VISIBLE
                        binding.recyclerFriends.visibility = View.GONE
                    }
                    FriendsViewModel.State.LOADING -> {
                        binding.progressBarFriends.visibility = View.VISIBLE
                        binding.textViewFriendsError.visibility = View.GONE
                        binding.recyclerFriends.visibility = View.GONE
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