package com.yunho.orbittest

sealed interface SideEffect {
    data object Reserve : SideEffect
}
