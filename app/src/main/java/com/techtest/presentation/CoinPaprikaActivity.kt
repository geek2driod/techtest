package com.techtest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.techtest.core.designsystem.TechTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinPaprikaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechTestTheme {
                CoinPaprikaNavigation()
            }
        }
    }
}
