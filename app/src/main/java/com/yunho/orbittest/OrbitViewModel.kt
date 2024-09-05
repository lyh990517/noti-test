package com.yunho.orbittest

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class OrbitViewModel : ViewModel(), ContainerHost<UiState, SideEffect> {
    override val container = container<UiState, SideEffect>(UiState(0))

    fun reserve() = intent {
        postSideEffect(SideEffect.Reserve)
    }
}
