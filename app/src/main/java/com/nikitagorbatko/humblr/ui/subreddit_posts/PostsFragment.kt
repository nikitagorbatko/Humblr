package com.nikitagorbatko.humblr.ui.subreddit_posts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.api.pojos.SubredditDto
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
        val subreddit = args.subreddit
        binding.toolbarPosts.title = subreddit.displayName
        binding.toolbarPosts.setOnMenuItemClickListener { _ ->
            return@setOnMenuItemClickListener true
        }
        binding.toolbarPosts.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbarPosts.setOnMenuItemClickListener {
            showSubredditDialog(subreddit, view)
            true
        }

        val adapter = PostsAdapter {
            val action =
                PostsFragmentDirections.actionSubredditPostsFragmentToSinglePostFragment(it)
            findNavController().navigate(action)
        }

        binding.recyclerPosts.adapter = adapter.withLoadStateFooter(CommonLoadStateAdapter())

        viewModel.getPosts(args.subreddit.displayName).onEach {
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

    private fun showSubredditDialog(subreddit: SubredditDto, view: View) {
        val activity = requireActivity()

        val alertDialog = activity.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(
                    R.string.ok
                ) { _, _ ->

                }
                val subscribeText =
                    if (subreddit.subscribed)
                        R.string.unsubscribe_title
                    else
                        R.string.subscribe_title

                setNeutralButton(subscribeText) { _, _ ->
                    viewModel.viewModelScope.launch {
                        if (subreddit.subscribed) {
                            viewModel.unsubscribeFromSub(subreddit.name)
                            Snackbar.make(view, R.string.you_unsubscribed, Snackbar.LENGTH_LONG)
                                .show()
                        } else {
                            viewModel.subscribeToSub(subreddit.name)
                            Snackbar.make(view, R.string.you_subscribed, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                setNegativeButton(R.string.share) { _, _ ->
                    sendSubredditLink(subreddit.displayName)
                }
            }
            builder.setTitle(subreddit.title)
            builder.setMessage(subreddit.description + "\n\n${subreddit.subscribers} subscribers")
            builder.create()
        }

        alertDialog.show()
    }

    private fun sendSubredditLink(subredditName: String) {
        val link = "https://www.reddit.com/r/$subredditName"
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        requireContext().startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}