package com.roman.cryptotracker.crypto.presentation.coin

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
import java.time.ZonedDateTime

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    companion object {
        const val TAG = "CoinListViewModel"
    }

    private val _uiState = MutableStateFlow(CoinUiState())
    val uiState = _uiState
        .onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CoinUiState()
        )

    private val _events = Channel<CoinEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(event: CoinAction) {
        Log.d(TAG, "onEvent: ${event.javaClass.canonicalName}")

        when (event) {
            is CoinAction.OnCoinClick -> onCoinClick(event.coinUiModel)
            CoinAction.OnRefresh -> loadCoins()
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
                    _events.send(CoinEvent.Error(error))
                }
        }
    }

    private fun onCoinClick(coinUiModel: CoinUiModel) {
        Log.d(TAG, "onCoinClick: $coinUiModel")
        _uiState.update {
            it.copy(selectedCoin = coinUiModel)
        }

        viewModelScope.launch {
            coinRepository.getCoinHistory(
                coinId = coinUiModel.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now()
            ).onSuccess { history ->
                Log.d(TAG, "onCoinClick: onSuccess $history")
                // todo
            }.onError { error ->
                Log.e(TAG, "onCoinClick: onError $error")
                _events.send(CoinEvent.Error(error))
            }
        }
    }

}