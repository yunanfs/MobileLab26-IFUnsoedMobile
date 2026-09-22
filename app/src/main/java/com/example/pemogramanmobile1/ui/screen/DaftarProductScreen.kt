package com.example.pemogramanmobile1.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pemogramanmobile1.R
import com.example.pemogramanmobile1.data.dummy.DummyData
import com.example.pemogramanmobile1.data.model.Category
import com.example.pemogramanmobile1.data.model.Product
import kotlinx.coroutines.delay


// ============================================================
// DAFTAR PRODUCT SCREEN
// ============================================================

@Composable
fun DaftarProductScreen(
    navController: NavController? = null
) {

    // ========================================================
    // STATE KATEGORI
    // ========================================================

    var selectedCategoryId by remember {
        mutableIntStateOf(0)
    }


    // ========================================================
    // STATE SEARCH
    // ========================================================

    var searchQuery by remember {
        mutableStateOf("")
    }


    // ========================================================
    // STATE LOADING
    // ========================================================

    var isLoading by remember {
        mutableStateOf(false)
    }


    // ========================================================
    // STATE PRODUK
    // ========================================================

    var filteredProducts by remember {
        mutableStateOf(
            DummyData.products
        )
    }


    // ========================================================
    // FILTER PRODUK
    // ========================================================

    LaunchedEffect(
        selectedCategoryId,
        searchQuery
    ) {

        isLoading = true

        delay(1000)

        filteredProducts =
            DummyData.products.filter { product ->

                val categoryMatch =
                    selectedCategoryId == 0 ||
                            product.category_id ==
                            selectedCategoryId

                val searchMatch =
                    product.name.contains(
                        searchQuery,
                        ignoreCase = true
                    )

                categoryMatch &&
                        searchMatch
            }

        isLoading = false
    }


    // ========================================================
    // STATELESS SCREEN
    // ========================================================

    StatelessDaftarProduct(

        products =
            filteredProducts,

        categories =
            DummyData.categories,

        selectedCategoryId =
            selectedCategoryId,

        searchQuery =
            searchQuery,

        isLoading =
            isLoading,


        // ====================================================
        // PILIH KATEGORI
        // ====================================================

        onCategorySelected = { categoryId ->

            selectedCategoryId =
                categoryId
        },


        // ====================================================
        // SEARCH
        // ====================================================

        onSearchQueryChanged = { query ->

            searchQuery =
                query
        },


        // ====================================================
        // KLIK PRODUK
        // ====================================================

        onProductClick = { product ->

            navController?.navigate(
                "detail/${product.id}"
            )
        },


        // ====================================================
        // HUBUNGI KAMI
        // ====================================================

        onContactUsClick = {

            navController?.navigate(
                "hubungi_kami"
            )
        },


        // ====================================================
        // KERANJANG
        // ====================================================

        onCartClick = {

            navController?.navigate(
                "cart"
            )
        }
    )
}


// ============================================================
// STATELESS DAFTAR PRODUCT
// ============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatelessDaftarProduct(

    products: List<Product>,

    categories: List<Category>,

    selectedCategoryId: Int,

    searchQuery: String,

    isLoading: Boolean,

    onCategorySelected: (Int) -> Unit,

    onSearchQueryChanged: (String) -> Unit,

    onProductClick: (Product) -> Unit,

    onContactUsClick: () -> Unit,

    onCartClick: () -> Unit
) {

    // ========================================================
    // STATE DROPDOWN MENU
    // ========================================================

    var expanded by remember {
        mutableStateOf(false)
    }


    // ========================================================
    // SCAFFOLD
    // ========================================================

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            "Daftar Produk"
                    )
                },


                // =================================================
                // ACTIONS TOP BAR
                // =================================================

                actions = {


                    // =============================================
                    // KERANJANG + BADGE
                    // =============================================

                    BadgedBox(

                        badge = {

                            val totalItems =
                                CartManager.cartItems.sumOf {
                                    it.quantity
                                }


                            // Badge hanya tampil
                            // jika ada barang

                            if (totalItems > 0) {

                                Badge {

                                    Text(

                                        text =
                                            if (
                                                totalItems > 99
                                            ) {
                                                "99+"
                                            } else {
                                                totalItems
                                                    .toString()
                                            }
                                    )
                                }
                            }
                        }

                    ) {

                        IconButton(

                            onClick = {

                                onCartClick()
                            }

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.ShoppingCart,

                                contentDescription =
                                    "Keranjang"
                            )
                        }
                    }


                    // =============================================
                    // MENU MORE
                    // =============================================

                    Box {

                        IconButton(

                            onClick = {

                                expanded = true
                            }

                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.MoreVert,

                                contentDescription =
                                    "Menu"
                            )
                        }


                        // =========================================
                        // DROPDOWN MENU
                        // =========================================

                        DropdownMenu(

                            expanded =
                                expanded,

                            onDismissRequest = {

                                expanded = false
                            }

                        ) {

                            DropdownMenuItem(

                                text = {

                                    Text(
                                        text =
                                            "Hubungi Kami"
                                    )
                                },


                                onClick = {

                                    expanded = false

                                    onContactUsClick()
                                },


                                leadingIcon = {

                                    Icon(

                                        imageVector =
                                            Icons.Default.Email,

                                        contentDescription =
                                            "Hubungi Kami"
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }

    ) { innerPadding ->


        // ======================================================
        // ISI HALAMAN
        // ======================================================

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
        ) {


            // ==================================================
            // SEARCH BAR
            // ==================================================

            OutlinedTextField(

                value =
                    searchQuery,

                onValueChange =
                    onSearchQueryChanged,

                modifier =
                    Modifier.fillMaxWidth(),

                placeholder = {

                    Text(
                        text =
                            "Cari produk..."
                    )
                },

                leadingIcon = {

                    Icon(

                        imageVector =
                            Icons.Default.Search,

                        contentDescription =
                            "Cari"
                    )
                },

                singleLine = true
            )


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            // ==================================================
            // JUDUL KATEGORI
            // ==================================================

            Text(

                text =
                    "Kategori",

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


            // ==================================================
            // LIST KATEGORI
            // ==================================================

            LazyRow(

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {


                // ==============================================
                // SEMUA
                // ==============================================

                item {

                    CategoryItem(

                        category =
                            Category(
                                id = 0,
                                name = "Semua",
                                description = null,
                                products_count = null
                            ),

                        isSelected =
                            selectedCategoryId == 0,

                        onClick = {

                            onCategorySelected(0)
                        }
                    )
                }


                // ==============================================
                // KATEGORI DUMMY DATA
                // ==============================================

                items(categories) { category ->

                    CategoryItem(

                        category =
                            category,

                        isSelected =
                            selectedCategoryId ==
                                    category.id,

                        onClick = {

                            onCategorySelected(
                                category.id
                            )
                        }
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )


            // ==================================================
            // LOADING
            // ==================================================

            if (isLoading) {

                Box(

                    modifier =
                        Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator()
                }

            } else {


                // =================================================
                // PRODUK KOSONG
                // =================================================

                if (products.isEmpty()) {

                    Box(

                        modifier =
                            Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(

                            text =
                                "Produk tidak ditemukan.",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium
                        )
                    }

                } else {


                    // =============================================
                    // GRID PRODUK
                    // =============================================

                    LazyVerticalGrid(

                        columns =
                            GridCells.Fixed(2),

                        modifier =
                            Modifier.fillMaxSize(),

                        contentPadding =
                            PaddingValues(
                                bottom = 16.dp
                            ),

                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        items(products) { product ->

                            ProductItemCard(

                                product =
                                    product,

                                onClick = {

                                    onProductClick(
                                        product
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


// ============================================================
// PRODUCT ITEM CARD
// ============================================================

@Composable
fun ProductItemCard(

    product: Product,

    onClick: () -> Unit
) {

    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {

                    onClick()
                },

        elevation =
            CardDefaults
                .cardElevation(
                    defaultElevation = 4.dp
                )
    ) {

        Column(

            modifier =
                Modifier.padding(12.dp)
        ) {


            // =================================================
            // GAMBAR
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
                        .height(150.dp),

                contentScale =
                    ContentScale.Crop
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
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
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            // =================================================
            // HARGA
            // =================================================

            Text(

                text =
                    "Rp ${product.price}",

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,

                color =
                    MaterialTheme
                        .colorScheme
                        .primary,

                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )


            // =================================================
            // STOK
            // =================================================

            Text(

                text =
                    "Stok: ${product.stock}",

                style =
                    MaterialTheme
                        .typography
                        .bodySmall
            )
        }
    }
}


// ============================================================
// CATEGORY ITEM
// ============================================================

@Composable
fun CategoryItem(

    category: Category,

    isSelected: Boolean,

    onClick: () -> Unit
) {

    Card(

        modifier =
            Modifier.clickable {

                onClick()
            },

        colors =
            CardDefaults.cardColors(

                containerColor =

                    if (isSelected) {

                        MaterialTheme
                            .colorScheme
                            .primary

                    } else {

                        MaterialTheme
                            .colorScheme
                            .surfaceVariant
                    }
            )
    ) {

        Text(

            text =
                category.name,

            modifier =
                Modifier.padding(

                    horizontal = 16.dp,

                    vertical = 10.dp
                ),

            color =

                if (isSelected) {

                    MaterialTheme
                        .colorScheme
                        .onPrimary

                } else {

                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
                },

            fontWeight =

                if (isSelected) {

                    FontWeight.Bold

                } else {

                    FontWeight.Normal
                }
        )
    }
}