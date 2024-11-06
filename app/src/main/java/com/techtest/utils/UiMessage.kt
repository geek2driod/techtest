package com.techtest.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

@Stable
sealed interface UiMessage {
    data class StringType(val message: String) : UiMessage
    data class ResourceType(@StringRes val message: Int) : UiMessage
}
