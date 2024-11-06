package com.techtest.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtest.domain.usecase.GetCoinsUsecase
import com.techtest.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUsecase: GetCoinsUsecase
) : ViewModel() {
    private val _state = MutableStateFlow<CoinListState>(CoinListState.Loading)
    val state: StateFlow<CoinListState> = _state

    fun load() {
        loadCoins(force = false)
    }

    fun onRefreshCoins() {
        loadCoins(force = true)
    }

    private var loadCoinsJob: Job? = null
    private fun loadCoins(force: Boolean) {
        loadCoinsJob?.cancel()
        loadCoinsJob = viewModelScope.launch {
            getCoinsUsecase(force = force).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _state.update { CoinListState.Loading }
                    }

                    is Result.Success -> {
                        _state.update { CoinListState.Success(result.data) }
                    }

                    is Result.Error -> {
                        _state.update { CoinListState.Error(result.message) }
                    }
                }
            }
        }
    }
}