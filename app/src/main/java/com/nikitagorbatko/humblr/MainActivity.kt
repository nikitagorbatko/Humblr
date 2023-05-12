package com.nikitagorbatko.humblr


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nikitagorbatko.humblr.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.visibility = when(destination.id) {
                R.id.splashFragment, R.id.loginFragment, R.id.viewPagerFragment -> {
                    View.GONE
                }
                else -> {
                    View.VISIBLE
                }
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1000 && data != null) {
//            val exception = AuthorizationException.fromIntent(intent)
//            val tokenExchangeRequest = AppAuth.prepareTokenRequest(intent)
//            if (exception != null) {
//                Log.d("TAG auth123", exception.message ?: "authResponse handle exception")
//            } else {
//                CoroutineScope(Dispatchers.IO).launch {
//                    onCodeReceived(tokenExchangeRequest)
//                }
//            }
//        }
//    }
}