package com.example.pemogramanmobile1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pemogramanmobile1.ui.screen.CartScreen
import com.example.pemogramanmobile1.ui.screen.DaftarProductScreen
import com.example.pemogramanmobile1.ui.screen.DetailProductScreen
import com.example.pemogramanmobile1.ui.screen.HubungiKamiScreen
import com.example.pemogramanmobile1.ui.theme.PemogramanMobile1Theme

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            PemogramanMobile1Theme {

                val navController =
                    rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "daftar_produk"
                ) {


                    // =========================================
                    // DAFTAR PRODUK
                    // =========================================

                    composable("daftar_produk") {

                        DaftarProductScreen(
                            navController = navController
                        )
                    }


                    // =========================================
                    // DETAIL PRODUK
                    // =========================================

                    composable(
                        route = "detail/{productId}",

                        arguments = listOf(
                            navArgument("productId") {
                                type = NavType.IntType
                            }
                        )

                    ) { backStackEntry ->

                        val productId =
                            backStackEntry.arguments
                                ?.getInt("productId")
                                ?: 0

                        DetailProductScreen(
                            navController = navController,
                            productId = productId
                        )
                    }


                    // =========================================
                    // HUBUNGI KAMI
                    // =========================================

                    composable("hubungi_kami") {

                        HubungiKamiScreen(
                            navController = navController
                        )
                    }


                    // =========================================
                    // KERANJANG
                    // =========================================

                    composable("cart") {

                        CartScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}