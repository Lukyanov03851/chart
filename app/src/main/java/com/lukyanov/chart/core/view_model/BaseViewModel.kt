package com.lukyanov.chart.core.view_model

import androidx.lifecycle.ViewModel
import com.lukyanov.chart.core.view_model.mvi.MVI
import com.lukyanov.chart.core.view_model.mvi.ModelViewIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseViewModel<State : UiState, Effect : UiEffect>(
    initialState: State
) : ViewModel(),
    MVI<State, Effect> by ModelViewIntent(initialState) {

    val viewModelScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override fun onCleared() {
        super.onCleared()
        cancelMviScope()
        viewModelScope.cancel()
    }
}
