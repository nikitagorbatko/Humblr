package com.nikitagorbatko.humblr.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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

        val adapter = CommentsAdapter(
            onItemClick = {
                val action = SinglePostFragmentDirections.actionSinglePostFragmentToUserFragment(it)
                findNavController().navigate(action)
            },
            onVoteDown = {
                viewModel.voteDown(it)
            },
            onVoteUp = {
                viewModel.voteUp(it)
            },
            saveComment = {
                viewModel.saveComment(it)
            },
            unsaveComment = {
                viewModel.unsaveComment(it)
            }
        )

        binding.toolbarSinglePost.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.recyclerComments.adapter = adapter

        binding.toolbarSinglePost.title = args.post.data.title

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getComments(args.post.data.id)
            viewModel.comments.collect {
                if (it != null) {
                    adapter.addAll(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    SinglePostViewModel.State.PRESENT -> {
                        binding.recyclerComments.visibility = View.VISIBLE
                        binding.progressSinglePost.visibility = View.GONE
                        binding.textSinglePostError.visibility = View.GONE
                    }
                    SinglePostViewModel.State.ERROR -> {
                        binding.recyclerComments.visibility = View.GONE
                        binding.progressSinglePost.visibility = View.GONE
                        binding.textSinglePostError.visibility = View.VISIBLE
                    }
                    SinglePostViewModel.State.LOADING -> {
                        binding.recyclerComments.visibility = View.GONE
                        binding.progressSinglePost.visibility = View.VISIBLE
                        binding.textSinglePostError.visibility = View.GONE
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