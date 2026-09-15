package com.example.pemogramanmobile1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pemogramanmobile1.ui.screen.DaftarProductScreen
import com.example.pemogramanmobile1.ui.theme.PemogramanMobile1Theme

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PemogramanMobile1Theme {
                DaftarProductScreen()
            }
        }
    }
}