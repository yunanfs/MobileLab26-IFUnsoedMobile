package com.example.pemogramanmobile1.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pemogramanmobile1.R
import com.example.pemogramanmobile1.data.model.Product


// ============================================================
// CART ITEM
// ============================================================

data class CartItem(
    val product: Product,
    val quantity: Int
)


// ============================================================
// CART MANAGER
// ============================================================

object CartManager {

    val cartItems =
        mutableStateListOf<CartItem>()


    // ========================================================
    // TAMBAH PRODUK KE KERANJANG
    // ========================================================

    fun addToCart(
        product: Product,
        quantity: Int
    ) {

        val existingIndex =
            cartItems.indexOfFirst {
                it.product.id == product.id
            }


        if (existingIndex >= 0) {

            val existingItem =
                cartItems[existingIndex]

            val newQuantity =
                existingItem.quantity + quantity


            cartItems[existingIndex] =
                existingItem.copy(
                    quantity =
                        newQuantity
                            .coerceAtMost(product.stock)
                )

        } else {

            cartItems.add(
                CartItem(
                    product = product,
                    quantity =
                        quantity.coerceAtMost(
                            product.stock
                        )
                )
            )
        }
    }


    // ========================================================
    // TAMBAH JUMLAH
    // ========================================================

    fun increaseQuantity(
        productId: Int
    ) {

        val index =
            cartItems.indexOfFirst {
                it.product.id == productId
            }


        if (index >= 0) {

            val item =
                cartItems[index]


            if (item.quantity < item.product.stock) {

                cartItems[index] =
                    item.copy(
                        quantity =
                            item.quantity + 1
                    )
            }
        }
    }


    // ========================================================
    // KURANGI JUMLAH
    // ========================================================

    fun decreaseQuantity(
        productId: Int
    ) {

        val index =
            cartItems.indexOfFirst {
                it.product.id == productId
            }


        if (index >= 0) {

            val item =
                cartItems[index]


            if (item.quantity > 1) {

                cartItems[index] =
                    item.copy(
                        quantity =
                            item.quantity - 1
                    )

            } else {

                cartItems.removeAt(index)
            }
        }
    }


    // ========================================================
    // HAPUS PRODUK
    // ========================================================

    fun removeFromCart(
        productId: Int
    ) {

        cartItems.removeAll {
            it.product.id == productId
        }
    }


    // ========================================================
    // HITUNG JUMLAH SEMUA BARANG
    // ========================================================

    fun getTotalItemCount(): Int {

        return cartItems.sumOf {
            it.quantity
        }
    }


    // ========================================================
    // HITUNG TOTAL HARGA
    // ========================================================

    fun getTotalPrice(): Double {

        return cartItems.sumOf {
            it.product.price * it.quantity
        }
    }


    // ========================================================
    // KOSONGKAN KERANJANG
    // ========================================================

    fun clearCart() {

        cartItems.clear()
    }
}


// ============================================================
// CART SCREEN
// ============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController
) {

    val context =
        LocalContext.current


    Scaffold(

        // ====================================================
        // TOP APP BAR
        // ====================================================

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Keranjang"
                    )
                }
            )
        }

    ) { innerPadding ->


        // ====================================================
        // KERANJANG KOSONG
        // ====================================================

        if (CartManager.cartItems.isEmpty()) {

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
                        "Keranjang masih kosong.",

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

                        navController.popBackStack()
                    }
                ) {

                    Text(
                        text =
                            "Kembali Belanja"
                    )
                }
            }


        } else {


            // =================================================
            // KERANJANG TERISI
            // =================================================

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
            ) {


                // =============================================
                // DAFTAR PRODUK
                // =============================================

                LazyColumn(

                    modifier =
                        Modifier.weight(1f),

                    contentPadding =
                        PaddingValues(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    items(

                        items =
                            CartManager.cartItems,

                        key = {
                            it.product.id
                        }

                    ) { cartItem ->

                        CartItemCard(
                            cartItem = cartItem
                        )
                    }
                }


                // =============================================
                // TOTAL PESANAN
                // =============================================

                Card(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

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


                        // =====================================
                        // JUMLAH BARANG
                        // =====================================

                        Text(

                            text =
                                "Total ${CartManager.getTotalItemCount()} barang",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium
                        )


                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )


                        // =====================================
                        // TOTAL HARGA
                        // =====================================

                        Text(

                            text =
                                "Total: Rp ${CartManager.getTotalPrice()}",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.Bold
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        // =====================================
                        // BELI SEKARANG
                        // =====================================

                        Button(

                            onClick = {

                                Toast.makeText(

                                    context,

                                    "Pesanan berhasil diproses",

                                    Toast.LENGTH_SHORT

                                ).show()


                                CartManager.clearCart()


                                navController.popBackStack()
                            },

                            modifier =
                                Modifier.fillMaxWidth()
                        ) {

                            Text(
                                text =
                                    "Beli Sekarang"
                            )
                        }
                    }
                }
            }
        }
    }
}


// ============================================================
// CART ITEM CARD
// ============================================================

@Composable
fun CartItemCard(
    cartItem: CartItem
) {

    val product =
        cartItem.product


    Card(

        modifier =
            Modifier.fillMaxWidth(),

        elevation =
            CardDefaults
                .cardElevation(
                    defaultElevation = 3.dp
                )
    ) {

        Row(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {


            // =================================================
            // GAMBAR PRODUK
            // =================================================

            Image(

                painter =
                    painterResource(
                        id =
                            R.drawable.dummy_product
                    ),

                contentDescription =
                    product.name,

                modifier =
                    Modifier.size(80.dp),

                contentScale =
                    ContentScale.Crop
            )


            Spacer(
                modifier =
                    Modifier.size(12.dp)
            )


            // =================================================
            // INFORMASI PRODUK
            // =================================================

            Column(

                modifier =
                    Modifier.weight(1f)
            ) {

                Text(

                    text =
                        product.name,

                    fontWeight =
                        FontWeight.Bold
                )


                Spacer(
                    modifier =
                        Modifier.height(4.dp)
                )


                Text(

                    text =
                        "Rp ${product.price}"
                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                // =============================================
                // KONTROL JUMLAH
                // =============================================

                Row(

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {


                    // =========================================
                    // KURANG
                    // =========================================

                    IconButton(

                        onClick = {

                            CartManager
                                .decreaseQuantity(
                                    product.id
                                )
                        }
                    ) {

                        Text(
                            text = "-"
                        )
                    }


                    // =========================================
                    // JUMLAH
                    // =========================================

                    Text(

                        text =
                            cartItem.quantity.toString(),

                        fontWeight =
                            FontWeight.Bold
                    )


                    // =========================================
                    // TAMBAH
                    // =========================================

                    IconButton(

                        onClick = {

                            CartManager
                                .increaseQuantity(
                                    product.id
                                )
                        }
                    ) {

                        Text(
                            text = "+"
                        )
                    }
                }
            }


            // =================================================
            // TOMBOL HAPUS
            // =================================================

            IconButton(

                onClick = {

                    CartManager
                        .removeFromCart(
                            product.id
                        )
                }
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Delete,

                    contentDescription =
                        "Hapus"
                )
            }
        }
    }
}