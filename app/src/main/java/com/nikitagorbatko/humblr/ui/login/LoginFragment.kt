package com.nikitagorbatko.humblr.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nikitagorbatko.humblr.AppAuth
import com.nikitagorbatko.humblr.MainActivity
import com.nikitagorbatko.humblr.R
import com.nikitagorbatko.humblr.data.preferences.SharedPreferencesRepository
import com.nikitagorbatko.humblr.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import org.koin.android.ext.android.inject

private const val REQUEST_CODE = 1000


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var authService: AuthorizationService

    private val repository: SharedPreferencesRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authService = AuthorizationService(requireContext())

        binding.buttonSignIn.setOnClickListener {
            startActivityForResult(getAuthorizationIntent(), REQUEST_CODE)
        }
    }

    private suspend fun onCodeReceived(tokenExchangeRequest: TokenRequest) {
        runCatching {
            AppAuth.performTokenRequestSuspend(
                authService = authService,
                tokenRequest = tokenExchangeRequest
            )
        }.onSuccess {
            repository.setToken("bearer $it")
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.action_loginFragment_to_navigation_home)
            }
            Log.d("TAG auth123", "bearer $it")
        }.onFailure {
            val errorMessage = it.message
            Log.d("TAG auth123", errorMessage ?: "error")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            val exception = AuthorizationException.fromIntent(data)
            val tokenExchangeRequest = AppAuth.prepareTokenRequest(data)
            if (exception != null) {
                Log.d("TAG auth123", exception.message ?: "authResponse handle exception")
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
//                CoroutineScope(Dispatchers.IO).launch {
                    onCodeReceived(tokenExchangeRequest)
                }
            }
        }
    }

    private fun getAuthorizationIntent(): Intent {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        return authService.getAuthorizationRequestIntent(
            AppAuth.authRequest,
            customTabsIntent
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}