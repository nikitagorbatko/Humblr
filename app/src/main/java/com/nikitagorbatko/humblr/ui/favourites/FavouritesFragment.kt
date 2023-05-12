package com.nikitagorbatko.humblr.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.databinding.FragmentFavouritesBinding
import com.nikitagorbatko.humblr.ui.CommonLoadStateAdapter
import com.nikitagorbatko.humblr.ui.subreddits.SubredditsAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouritesViewModel by viewModel()
    private lateinit var subredditsAdapter: SubredditsAdapter
    private lateinit var commentsAdapter: CommentsAdapter

    private var firstGroupChipType = FirstGroupChip.SUBREDDITS
    private var secondGroupChipType = SecondGroupChip.ALL

    private var currentAdapterType = AdapterType.SUB_ADAPTER

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeAdapters()
        bind()
        observe()
    }



    private fun initializeAdapters() {
        subredditsAdapter = SubredditsAdapter(
            context = requireContext(),
            onItemClick = {
                val action =
                    FavouritesFragmentDirections.actionNavigationFavouritesToSubredditPostsFragment(
                        it
                    )
                findNavController().navigate(action)
            },
            onAddClick = { subscribed, name ->
                //wihtout reacting for result
                viewModel.viewModelScope.launch {
                    if (subscribed) {
                        viewModel.unsubscribeFromSub(name)
                    } else {
                        viewModel.subscribeToSub(name)
                    }
                }
            }
        )

        commentsAdapter = CommentsAdapter(
            onItemClick = {
                val action =
                    FavouritesFragmentDirections.actionNavigationFavouritesToUserFragment(
                        it
                    )
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
    }

    private fun bind() {
        binding.firstGroup.check(R.id.chip_subreddits)
        binding.secondGroup.check(R.id.chip_all)
        binding.recyclerUniversal.adapter =
            subredditsAdapter.withLoadStateFooter(CommonLoadStateAdapter())

        binding.chipComments.setOnClickListener {
            if (firstGroupChipType != FirstGroupChip.COMMENTS) {
                firstGroupChipType = FirstGroupChip.COMMENTS
                onChipClick()
            }
        }

        binding.chipSubreddits.setOnClickListener {
            if (firstGroupChipType != FirstGroupChip.SUBREDDITS) {
                firstGroupChipType = FirstGroupChip.SUBREDDITS
                onChipClick()
            }
        }

        binding.chipAll.setOnClickListener {
            if (secondGroupChipType != SecondGroupChip.ALL) {
                secondGroupChipType = SecondGroupChip.ALL
                onChipClick()
            }
        }

        binding.chipSaved.setOnClickListener {
            if (secondGroupChipType != SecondGroupChip.SAVED) {
                secondGroupChipType = SecondGroupChip.SAVED
                onChipClick()
            }
        }
    }

    private fun onChipClick() {
        subredditsAdapter.submitData(lifecycle, PagingData.empty())
        commentsAdapter.submitData(lifecycle, PagingData.empty())
        when {
            firstGroupChipType == FirstGroupChip.SUBREDDITS && secondGroupChipType == SecondGroupChip.ALL -> {
                checkChangeAdapter(AdapterType.SUB_ADAPTER)
                viewModel.getAllSubreddits().onEach {
                    subredditsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            firstGroupChipType == FirstGroupChip.SUBREDDITS && secondGroupChipType == SecondGroupChip.SAVED -> {
                checkChangeAdapter(AdapterType.SUB_ADAPTER)
                viewModel.getFavouriteSubreddits().onEach {
                    subredditsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            firstGroupChipType == FirstGroupChip.COMMENTS && secondGroupChipType == SecondGroupChip.ALL -> {
                checkChangeAdapter(AdapterType.SUB_ADAPTER)
                viewModel.getAllSubreddits().onEach {
                    subredditsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            firstGroupChipType == FirstGroupChip.COMMENTS && secondGroupChipType == SecondGroupChip.SAVED -> {
                checkChangeAdapter(AdapterType.COMM_ADAPTER)
                viewModel.getSavedComments().onEach {
                    commentsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
    }

    private fun checkChangeAdapter(neededAdapter: AdapterType) {
        if (currentAdapterType != neededAdapter) {
            when (neededAdapter) {
                AdapterType.COMM_ADAPTER -> {
                    binding.recyclerUniversal.adapter =
                        commentsAdapter.withLoadStateFooter(CommonLoadStateAdapter())
                    currentAdapterType = AdapterType.COMM_ADAPTER
                }
                AdapterType.SUB_ADAPTER -> {
                    binding.recyclerUniversal.adapter =
                        subredditsAdapter.withLoadStateFooter(CommonLoadStateAdapter())
                    currentAdapterType = AdapterType.SUB_ADAPTER
                }
            }
        }
    }

    private fun observe() {
        viewModel.getAllSubreddits().onEach {
            subredditsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                merge(subredditsAdapter.loadStateFlow, commentsAdapter.loadStateFlow).collect {
                    binding.progressFavorites.visibility =
                        if (it.source.refresh is LoadState.Loading) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                    binding.textFavoritesError.visibility =
                        if (it.source.refresh is LoadState.Error) {
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

    private enum class FirstGroupChip {
        SUBREDDITS, COMMENTS
    }

    private enum class SecondGroupChip {
        ALL, SAVED
    }

    private enum class AdapterType {
        SUB_ADAPTER, COMM_ADAPTER
    }
}