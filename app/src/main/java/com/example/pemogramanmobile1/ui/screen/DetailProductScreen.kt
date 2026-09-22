package com.example.pemogramanmobile1.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pemogramanmobile1.R
import com.example.pemogramanmobile1.data.dummy.DummyData
import com.example.pemogramanmobile1.data.model.Product


// ============================================================
// DETAIL PRODUCT SCREEN
// ============================================================

@Composable
fun DetailProductScreen(
    navController: NavController,
    productId: Int
) {

    val product =
        DummyData.products.find {
            it.id == productId
        }


    StatelessDetailProduct(

        product = product,

        onBackClick = {

            navController.popBackStack()
        }
    )
}


// ============================================================
// STATELESS DETAIL PRODUCT
// ============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessDetailProduct(
    product: Product?,
    onBackClick: () -> Unit
) {

    val context =
        LocalContext.current


    // ========================================================
    // JUMLAH PRODUK
    // ========================================================

    var quantity by remember {

        mutableIntStateOf(1)
    }


    // ========================================================
    // JIKA PRODUK TIDAK DITEMUKAN
    // ========================================================

    if (product == null) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {

                        Text(
                            text = "Detail Produk"
                        )
                    }
                )
            }

        ) { innerPadding ->

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),

                horizontalAlignment =
                    Alignment.CenterHorizontally,

                verticalArrangement =
                    Arrangement.Center
            ) {

                Text(

                    text =
                        "Produk tidak ditemukan.",

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium
                )


                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )


                Button(

                    onClick = {
                        onBackClick()
                    }
                ) {

                    Text(
                        text = "Kembali"
                    )
                }
            }
        }

        return
    }


    // ========================================================
    // DETAIL PRODUK
    // ========================================================

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Detail Produk"
                    )
                }
            )
        }

    ) { innerPadding ->


        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(16.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {


            // =================================================
            // GAMBAR PRODUK
            // =================================================

            val imageRes =

                if (
                    product.img ==
                    "dummy_product"
                ) {

                    R.drawable.dummy_product

                } else {

                    R.drawable.dummy_product
                }


            Image(

                painter =
                    painterResource(
                        id = imageRes
                    ),

                contentDescription =
                    product.name,

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp),

                contentScale =
                    ContentScale.Crop
            )


            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )


            // =================================================
            // NAMA PRODUK
            // =================================================

            Text(

                text =
                    product.name,

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                fontWeight =
                    FontWeight.Bold,

                modifier =
                    Modifier.fillMaxWidth()
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            // =================================================
            // HARGA PRODUK
            // =================================================

            Text(

                text =
                    "Rp ${product.price}",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold,

                color =
                    MaterialTheme
                        .colorScheme
                        .primary,

                modifier =
                    Modifier.fillMaxWidth()
            )


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            // =================================================
            // INFORMASI PRODUK
            // =================================================

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                elevation =
                    CardDefaults
                        .cardElevation(
                            defaultElevation = 4.dp
                        )
            ) {

                Column(

                    modifier =
                        Modifier.padding(16.dp)
                ) {


                    // =========================================
                    // DESKRIPSI
                    // =========================================

                    Text(

                        text =
                            "Deskripsi",

                        style =
                            MaterialTheme
                                .typography
                                .titleMedium,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    Text(

                        text =
                            product.description
                                ?: "Tidak ada deskripsi."
                    )


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    // =========================================
                    // STOK
                    // =========================================

                    Text(

                        text =
                            "Stok: ${product.stock}",

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )


            // =================================================
            // JUMLAH BELI
            // =================================================

            Text(

                text =
                    "Jumlah Beli",

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                modifier =
                    Modifier.fillMaxWidth()
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            // =================================================
            // KONTROL JUMLAH
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically,

                horizontalArrangement =
                    Arrangement.Center
            ) {


                // =============================================
                // TOMBOL KURANG
                // =============================================

                IconButton(

                    onClick = {

                        if (quantity > 1) {

                            quantity--
                        }
                    },

                    modifier =
                        Modifier.size(48.dp)
                ) {

                    Text(

                        text = "-",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge
                    )
                }


                // =============================================
                // JUMLAH
                // =============================================

                Text(

                    text =
                        quantity.toString(),

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    fontWeight =
                        FontWeight.Bold,

                    modifier =
                        Modifier.padding(
                            horizontal = 24.dp
                        )
                )


                // =============================================
                // TOMBOL TAMBAH
                // =============================================

                IconButton(

                    onClick = {

                        if (
                            quantity <
                            product.stock
                        ) {

                            quantity++

                        } else {

                            Toast.makeText(

                                context,

                                "Jumlah tidak boleh melebihi stok",

                                Toast.LENGTH_SHORT

                            ).show()
                        }
                    },

                    modifier =
                        Modifier.size(48.dp)
                ) {

                    Text(

                        text = "+",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            // =================================================
            // TAMBAH KE KERANJANG
            // =================================================

            Button(

                onClick = {


                    // =========================================
                    // SIMPAN PRODUK KE KERANJANG
                    // =========================================

                    CartManager.addToCart(

                        product =
                            product,

                        quantity =
                            quantity
                    )


                    // =========================================
                    // NOTIFIKASI
                    // =========================================

                    Toast.makeText(

                        context,

                        "${product.name} sebanyak $quantity berhasil ditambahkan ke keranjang",

                        Toast.LENGTH_SHORT

                    ).show()


                    // =========================================
                    // RESET JUMLAH
                    // =========================================

                    quantity = 1


                    // =========================================
                    // KEMBALI KE BERANDA
                    // =========================================

                    onBackClick()
                },

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    text =
                        "Tambah ke Keranjang"
                )
            }


            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )


            // =================================================
            // TOMBOL KEMBALI
            // =================================================

            Button(

                onClick = {

                    onBackClick()
                },

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(
                    text =
                        "Kembali"
                )
            }
        }
    }
}


// ============================================================
// PREVIEW
// ============================================================

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true
)
@Composable
fun StatelessDetailProductPreview() {

    StatelessDetailProduct(

        product =
            DummyData.products[0],

        onBackClick = {}
    )
}