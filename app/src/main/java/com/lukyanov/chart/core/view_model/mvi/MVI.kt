package com.lukyanov.chart.core.view_model.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MVI<State, Effect> {

    val uiState: StateFlow<State>
    val uiEffect: Flow<Effect>

    fun publishState(transformation: State.() -> State)
    fun publishEffect(effect: Effect)
    fun cancelMviScope()
}
