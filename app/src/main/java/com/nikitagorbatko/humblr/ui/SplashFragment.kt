package com.nikitagorbatko.humblr.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val repository: SharedPreferencesRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            val a = repository.getToken()

            Log.d("Tag token", a?: "")
            when {
                repository.getIsFirstStart() -> {
                    val action = SplashFragmentDirections.actionSplashFragmentToViewPagerFragment()
                    findNavController().navigate(action)
                }
                repository.getToken() != null -> {
                    val action = SplashFragmentDirections.actionSplashFragmentToNavigationHome()
                    //check if the token is fresh
                    findNavController().navigate(action)
                }
                else -> {
                    val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

