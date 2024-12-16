package com.roman.cryptotracker.core.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.roman.cryptotracker.core.presentation.lifecycle.Observe
import com.roman.cryptotracker.core.presentation.lifecycle.ObserveAsEvents
import com.roman.cryptotracker.core.presentation.util.toString
import com.roman.cryptotracker.crypto.presentation.coin.CoinAction
import com.roman.cryptotracker.crypto.presentation.coin.CoinEvent
import com.roman.cryptotracker.crypto.presentation.coin.CoinViewModel
import com.roman.cryptotracker.crypto.presentation.coin.details.CoinDetailScreenView
import com.roman.cryptotracker.crypto.presentation.coin.list.CoinListScreenView
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptiveCoinListDetailPaneView(
    modifier: Modifier = Modifier,
    viewModel: CoinViewModel = koinViewModel(),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LocalLifecycleOwner.current.lifecycle.Observe(viewModel::onLifecycleEvent)

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is CoinEvent.Error -> {
                Toast.makeText(context, event.error.toString(context), Toast.LENGTH_LONG).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreenView(
                    uiState = uiState,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is CoinAction.OnCoinClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }

                            CoinAction.OnRefresh -> {}
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreenView(
                    uiState = uiState,
                )
            }
        }
    )
}