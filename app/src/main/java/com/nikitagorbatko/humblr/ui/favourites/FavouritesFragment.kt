package com.nikitagorbatko.humblr.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.nikitagorbatko.humblr.ui.subreddits.SubredditsFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouritesViewModel by viewModel()
    private lateinit var adapter: SubredditsAdapter

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

        bind()
        observe()
    }

    private fun bind() {
        binding.firstGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when (checkedIds) {
                //R.id.ch
            }
        }
        binding.chipAll.setOnClickListener {
            adapter.submitData(lifecycle, PagingData.empty())
            viewModel.getAllSubreddits().onEach {
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
        binding.chipSaved.setOnClickListener {
            adapter.submitData(lifecycle, PagingData.empty())
            viewModel.getFavouriteSubreddits().onEach {
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
//        binding.secondGroup.setOnCheckedStateChangeListener { group, checkedIds ->
//            when (checkedIds) {
//                R.id.chip_all -> {
//                    adapter.submitData(lifecycle, PagingData.empty())
//                    viewModel.getAllSubreddits().onEach {
//                        adapter.submitData(it)
//                    }.launchIn(viewLifecycleOwner.lifecycleScope)
//                }
//                R.id.ship_saved -> {
//                    adapter.submitData(lifecycle, PagingData.empty())
//                    viewModel.getFavouriteSubreddits().onEach {
//                        adapter.submitData(it)
//                    }.launchIn(viewLifecycleOwner.lifecycleScope)
//                }
//            }
//        }
    }

    private fun observe() {
        adapter = SubredditsAdapter(
            context = requireContext(),
            onItemClick = {
//                val action =
//                    SubredditsFragmentDirections.actionNavigationSubredditsToSubredditPostsFragment(
//                        it
//                    )
//                findNavController().navigate(action)
            },
            onAddClick = { subscribed, name ->
                //wihtout reacting for result
//                viewModel.viewModelScope.launch {
//                    if (subscribed) {
//                        viewModel.unsubscribeFromSub(name)
//                    } else {
//                        viewModel.subscribeToSub(name)
//                    }
//                }
            }
        )

        binding.recyclerUniversal.adapter =
            adapter.withLoadStateFooter(CommonLoadStateAdapter())

        viewModel.getAllSubreddits().onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
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
}