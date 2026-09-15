package com.example.pemogramanmobile1.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pemogramanmobile1.R
import com.example.pemogramanmobile1.data.dummy.DummyData
import com.example.pemogramanmobile1.data.model.Category
import com.example.pemogramanmobile1.data.model.Product
import com.example.pemogramanmobile1.ui.theme.PemogramanMobile1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItemCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp
                        )
                    )
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.dummy_product
                    ),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = product.description ?: "Tidak ada deskripsi",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Rp ${product.price.toInt()}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Stok: ${product.stock}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = category.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "${category.products_count ?: 0} Produk",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarProductScreen() {

    var selectedCategoryId by remember {
        mutableStateOf<Int?>(null)
    }

    val context = LocalContext.current

    val filteredProducts = if (selectedCategoryId == null) {
        DummyData.products
    } else {
        DummyData.products.filter {
            it.category_id == selectedCategoryId
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Daftar Produk UMKM"
                    )
                },
                actions = {
                    Text(
                        text = "🛒",
                        modifier = Modifier.padding(
                            end = 16.dp
                        )
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Text(
                text = "Kategori Produk",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    12.dp
                )
            ) {

                item {

                    CategoryItem(
                        category = Category(
                            id = 0,
                            name = "Semua",
                            description = "Semua produk",
                            products_count = DummyData.products.size
                        ),
                        isSelected = selectedCategoryId == null,
                        onClick = {
                            selectedCategoryId = null
                        }
                    )
                }

                items(DummyData.categories) { category ->

                    CategoryItem(
                        category = category,
                        isSelected = selectedCategoryId == category.id,
                        onClick = {
                            selectedCategoryId = category.id
                        }
                    )
                }
            }

            Text(
                text = "Daftar Produk",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(filteredProducts) { product ->

                    ProductItemCard(
                        product = product,
                        onClick = {
                            Toast.makeText(
                                context,
                                "Clicked: ${product.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun PreviewProduct() {
    PemogramanMobile1Theme {
        ProductItemCard(
            product = DummyData.products[0],
            onClick = {}
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun PreviewCategory() {
    PemogramanMobile1Theme {
        CategoryItem(
            category = DummyData.categories[0],
            isSelected = true,
            onClick = {}
        )
    }
}