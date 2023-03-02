package com.example.ramalancuaca.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
fun Fragment.createComposeView(layout: @Composable () -> Unit): ComposeView {
    return ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            layout()
        }

    }
}
