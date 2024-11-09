package com.roman.cryptotracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.roman.cryptotracker.core.presentation.lifecycle.Observe
import com.roman.cryptotracker.core.presentation.lifecycle.ObserveAsEvents
import com.roman.cryptotracker.core.presentation.util.toString
import com.roman.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.roman.cryptotracker.crypto.presentation.coin_list.CoinListScreenView
import com.roman.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.roman.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CryptoTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->


                    // TODO: move to navigation component
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()
                    LocalLifecycleOwner.current.lifecycle.Observe(viewModel::onLifecycleEvent)

                    val context = LocalContext.current
                    ObserveAsEvents(viewModel.events) {
                        when (it) {
                            is CoinListEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    it.error.toString(context),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    CoinListScreenView(
                        modifier = Modifier.padding(innerPadding),
                        uiState = state,
                        onAction = viewModel::onAction
                    )


                }
            }
        }
    }
}