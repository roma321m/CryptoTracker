package com.roman.cryptotracker.crypto.presentation.coin_list

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.cryptotracker.core.domain.util.onError
import com.roman.cryptotracker.core.domain.util.onSuccess
import com.roman.cryptotracker.crypto.domain.CoinRepository
import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel
import com.roman.cryptotracker.crypto.presentation.models.toCoinUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    companion object {
        const val TAG = "CoinListViewModel"
    }

    private val _uiState = MutableStateFlow(CoinListUiState())
    val uiState = _uiState
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CoinListUiState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(event: CoinListAction) {
        Log.d(TAG, "onEvent: ${event.javaClass.canonicalName}")

        when (event) {
            is CoinListAction.OnCoinClick -> onCoinClick(event.coinUiModel)
            CoinListAction.OnRefresh -> loadCoins()
        }
    }

    fun onLifecycleEvent(event: Lifecycle.Event) {
        Log.d(TAG, "onLifecycleEvent: ${event.name}")

        when (event) {
            Lifecycle.Event.ON_CREATE -> Unit
            else -> Unit
        }
    }

    private fun loadCoins() {
        Log.d(TAG, "loadCoins")
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            coinRepository.getCoins()
                .onSuccess { coins ->
                    Log.d(TAG, "loadCoins: onSuccess")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { coin ->
                                coin.toCoinUiModel()
                            }
                        )
                    }
                }.onError { error ->
                    Log.e(TAG, "loadCoins: onError $error")
                    _uiState.update { it.copy(isLoading = false) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }

    private fun onCoinClick(coinUiModel: CoinUiModel) {
        Log.d(TAG, "onCoinClick: $coinUiModel")
        // TODO: navigate to coin details
    }

}