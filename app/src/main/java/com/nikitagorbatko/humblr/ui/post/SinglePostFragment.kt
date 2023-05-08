package com.nikitagorbatko.humblr.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nikitagorbatko.humblr.databinding.FragmentSinglePostBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SinglePostFragment : Fragment() {
    private var _binding: FragmentSinglePostBinding? = null
    private val binding get() = _binding!!

    private val args: SinglePostFragmentArgs by navArgs()
    private val viewModel: SinglePostViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSinglePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CommentsAdapter() {
            val action = SinglePostFragmentDirections.actionSinglePostFragmentToUserFragment(it)
            findNavController().navigate(action)
        }

        binding.recyclerComments.adapter = adapter

        viewModel.viewModelScope.launch {
            viewModel.getComments(args.id)
            viewModel.comments.collect {
                if (it != null) {
                    adapter.addAll(it)
                }
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.state.collect {
                when (it) {
                    SinglePostViewModel.State.PRESENT -> {
                        binding.recyclerComments.visibility = View.VISIBLE
                        binding.progressBarSinglePost.visibility = View.GONE
                        binding.textViewSinglePostError.visibility = View.GONE
                    }
                    SinglePostViewModel.State.ERROR -> {
                        binding.recyclerComments.visibility = View.GONE
                        binding.progressBarSinglePost.visibility = View.GONE
                        binding.textViewSinglePostError.visibility = View.VISIBLE
                    }
                    SinglePostViewModel.State.LOADING -> {
                        binding.recyclerComments.visibility = View.GONE
                        binding.progressBarSinglePost.visibility = View.VISIBLE
                        binding.textViewSinglePostError.visibility = View.GONE
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