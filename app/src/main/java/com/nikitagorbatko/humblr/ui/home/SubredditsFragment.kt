package com.nikitagorbatko.humblr.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nikitagorbatko.humblr.MainActivity
import com.nikitagorbatko.humblr.databinding.FragmentSubredditsBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SubredditsFragment : Fragment() {

    private var _binding: FragmentSubredditsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubredditsViewModel by viewModel(parameters = { parametersOf("") })

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
        val adapter = SubredditsAdapter() {

        }

        binding.recyclerViewSubreddits.adapter = adapter

        viewModel.getPhotosBySearch().onEach {
            adapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val inputText = binding.searchEditText.text.toString()
//                if (CityValidator.isCityNameValid(inputText)) {
//                    viewModel.getCityWeather(inputText)
//                } else {
                Toast.makeText(requireContext(), "Название некорректно", Toast.LENGTH_LONG).show()
//                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.searchInputLayout.setEndIconOnClickListener {
            val inputText = binding.searchEditText.text.toString()
//            if (CityValidator.isCityNameValid(inputText)) {
//                viewModel.getCityWeather(inputText)
//            } else {
            Toast.makeText(requireContext(), "Название некорректно", Toast.LENGTH_LONG).show()
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}