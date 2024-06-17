package com.example.mymoneytracker.ui.summary

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    class StringResources(
            @StringRes val resId: Int,
            vararg val args: Any
    ): UiText()

    @Composable
    fun asString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResources -> stringResource(resId, *args)
        }
    }
}