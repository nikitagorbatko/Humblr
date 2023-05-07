package com.nikitagorbatko.humblr.ui.subreddits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.google.android.material.tabs.TabLayout
import com.nikitagorbatko.humblr.MainActivity
import com.nikitagorbatko.humblr.databinding.FragmentSubredditsBinding
import com.nikitagorbatko.humblr.ui.CommonLoadStateAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SubredditsFragment : Fragment() {
    private var _binding: FragmentSubredditsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubredditsViewModel by viewModel()
    private lateinit var adapter: SubredditsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).showBottom()
        _binding = FragmentSubredditsBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        bind()
    }

    private fun observe() {
        adapter = SubredditsAdapter(
            context = requireContext(),
            onItemClick = {
                val action =
                    SubredditsFragmentDirections.actionNavigationSubredditsToSubredditPostsFragment(
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

        binding.recyclerViewSubreddits.adapter =
            adapter.withLoadStateFooter(CommonLoadStateAdapter())

        viewModel.getNewSubreddits().onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    //binding.swipeRefreshLayout.isRefreshing = it.source.refresh is LoadState.Loading
                    binding.progressBarSubreddits.visibility =
                        if (it.source.refresh is LoadState.Loading) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                }
            }
        }
    }


    private fun bind() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = binding.searchEditText.text.toString()
                getQuerySubreddits(inputText)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.searchInputLayout.setEndIconOnClickListener {
            val inputText = binding.searchEditText.text.toString()
            getQuerySubreddits(inputText)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.searchEditText.setText("")

                val inputMethodManager: InputMethodManager =
                    getSystemService(
                        requireContext(),
                        InputMethodManager::class.java
                    ) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                when (tab?.position) {
                    0 -> {
                        viewModel.getNewSubreddits().onEach {
                            adapter.submitData(it)
                        }.launchIn(viewLifecycleOwner.lifecycleScope)
                    }
                    else -> {
                        viewModel.getPopularSubreddits().onEach {
                            adapter.submitData(it)
                        }.launchIn(viewLifecycleOwner.lifecycleScope)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                adapter.submitData(lifecycle, PagingData.empty())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun getQuerySubreddits(query: String) {
        val inputText = binding.searchEditText.text.toString()
        adapter.submitData(lifecycle, PagingData.empty())
        if (inputText.isNotEmpty()) {
            viewModel.getQuerySubreddits(query = query).onEach {
                adapter.submitData(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        } else {
            Toast.makeText(requireContext(), "Название некорректно", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}