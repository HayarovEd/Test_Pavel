package com.edurda77.test_pavel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.edurda77.test_pavel.ui.main_screen.MainScreen
import com.edurda77.test_pavel.ui.theme.Test_PavelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Test_PavelTheme {
                MainScreen()
            }
        }
    }
}