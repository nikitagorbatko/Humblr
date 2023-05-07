package com.nikitagorbatko.humblr.ui.subreddit_posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.nikitagorbatko.humblr.databinding.FragmentPostsBinding
import com.nikitagorbatko.humblr.ui.CommonLoadStateAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private val args: PostsFragmentArgs by navArgs()
    private val viewModel: PostsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = args.displayName
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener {
            true
        }

        val adapter = PostsAdapter {
            val action =
                PostsFragmentDirections.actionSubredditPostsFragmentToSinglePostFragment(it)
            findNavController().navigate(action)
        }

        binding.recyclerPosts.adapter = adapter.withLoadStateFooter(CommonLoadStateAdapter())

        viewModel.getPosts(args.displayName).onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    //binding.swipeRefreshLayout.isRefreshing = it.source.refresh is LoadState.Loading
                    binding.progressBarPosts.visibility =
                        if (it.source.refresh is LoadState.Loading) {
                            View.VISIBLE
                        } else {
                            View.GONE
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