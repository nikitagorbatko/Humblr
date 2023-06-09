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
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.databinding.FragmentFavouritesBinding
import com.nikitagorbatko.humblr.ui.CommonLoadStateAdapter
import com.nikitagorbatko.humblr.ui.subreddits.SubredditsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
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

    private var adapterType = AdapterType.SUB_ADAPTER
    private var adapterLoadStateJob: Job? = null

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
            onVoteDown = { viewModel.voteDown(it) },
            onVoteUp = { viewModel.voteUp(it) },
            saveComment = { viewModel.saveComment(it) },
            unsaveComment = { viewModel.unsaveComment(it) }
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
                checkAdapterType(AdapterType.SUB_ADAPTER)
                viewModel.getAllSubreddits().onEach {
                    subredditsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            firstGroupChipType == FirstGroupChip.SUBREDDITS && secondGroupChipType == SecondGroupChip.SAVED -> {
                checkAdapterType(AdapterType.SUB_ADAPTER)
                viewModel.getFavouriteSubreddits().onEach {
                    subredditsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            firstGroupChipType == FirstGroupChip.COMMENTS && secondGroupChipType == SecondGroupChip.ALL -> {
                checkAdapterType(AdapterType.SUB_ADAPTER)
                viewModel.getAllSubreddits().onEach {
                    subredditsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
            firstGroupChipType == FirstGroupChip.COMMENTS && secondGroupChipType == SecondGroupChip.SAVED -> {
                checkAdapterType(AdapterType.COMM_ADAPTER)
                viewModel.getSavedComments().onEach {
                    commentsAdapter.submitData(it)
                }.launchIn(viewLifecycleOwner.lifecycleScope)
            }
        }
    }

    private fun checkAdapterType(neededAdapter: AdapterType) {
        if (adapterType != neededAdapter) {
            when (neededAdapter) {
                AdapterType.COMM_ADAPTER -> {
                    adapterLoadStateJob?.cancel()
                    adapterLoadStateJob = viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            commentsAdapter.loadStateFlow.collectLatest {
                                handleUi(it)
                            }
                        }
                    }
                    binding.recyclerUniversal.adapter =
                        commentsAdapter.withLoadStateFooter(CommonLoadStateAdapter())
                    adapterType = AdapterType.COMM_ADAPTER
                }
                AdapterType.SUB_ADAPTER -> {
                    adapterLoadStateJob?.cancel()
                    adapterLoadStateJob = viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            subredditsAdapter.loadStateFlow.collectLatest {
                                handleUi(it)
                            }
                        }
                    }
                    binding.recyclerUniversal.adapter =
                        subredditsAdapter.withLoadStateFooter(CommonLoadStateAdapter())
                    adapterType = AdapterType.SUB_ADAPTER
                }
            }
        }
    }


    private fun observe() {
        onChipClick()
        adapterLoadStateJob = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                subredditsAdapter.loadStateFlow.collectLatest {
                    handleUi(it)
                }
            }
        }
    }

    private fun handleUi(it: CombinedLoadStates) {
        when (it.source.refresh) {
            LoadState.Loading -> {
                binding.firstGroup.visibility = View.VISIBLE
                binding.secondGroup.visibility = View.VISIBLE
                binding.progressFavorites.visibility = View.VISIBLE
                binding.textFavoritesError.visibility = View.GONE
            }
            is LoadState.Error -> {
                binding.firstGroup.visibility = View.GONE
                binding.secondGroup.visibility = View.GONE
                binding.progressFavorites.visibility = View.GONE
                binding.textFavoritesError.visibility = View.VISIBLE
            }
            is LoadState.NotLoading -> {
                binding.firstGroup.visibility = View.VISIBLE
                binding.secondGroup.visibility = View.VISIBLE
                binding.progressFavorites.visibility = View.GONE
                binding.textFavoritesError.visibility = View.GONE
            }
        }
//        if (it.source.refresh is LoadState.Loading) {
//            binding.firstGroup.visibility = View.GONE
//            binding.secondGroup.visibility = View.GONE
//            binding.progressFavorites.visibility = View.VISIBLE
//        } else {
//            binding.firstGroup.visibility = View.VISIBLE
//            binding.secondGroup.visibility = View.VISIBLE
//            binding.progressFavorites.visibility = View.GONE
//        }
//        if (it.source.refresh is LoadState.Error) {
//            binding.firstGroup.visibility = View.GONE
//            binding.secondGroup.visibility = View.GONE
//            binding.textFavoritesError.visibility = View.VISIBLE
//        } else {
//            binding.firstGroup.visibility = View.VISIBLE
//            binding.secondGroup.visibility = View.VISIBLE
//            binding.textFavoritesError.visibility = View.GONE
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private enum class FirstGroupChip { SUBREDDITS, COMMENTS }

    private enum class SecondGroupChip { ALL, SAVED }

    private enum class AdapterType { SUB_ADAPTER, COMM_ADAPTER }
}