package com.example.pemogramanmobile1.data.dummy

import com.example.pemogramanmobile1.data.model.Category
import com.example.pemogramanmobile1.data.model.Product

object DummyData {

    val categories = listOf(
        Category(
            id = 1,
            name = "Makanan",
            description = "Aneka makanan lokal",
            products_count = 5
        ),
        Category(
            id = 2,
            name = "Minuman",
            description = "Minuman segar",
            products_count = 3
        ),
        Category(
            id = 3,
            name = "Kerajinan",
            description = "Kerajinan khas lokal",
            products_count = 2
        )
    )

    val products = listOf(
        Product(
            id = 1,
            category_id = 1,
            category = categories[0],
            name = "Mendoan",
            description = "Tempe mendoan khas Banyumas",
            price = 15000.0,
            stock = 20,
            img = "dummy_product"
        ),
        Product(
            id = 2,
            category_id = 1,
            category = categories[0],
            name = "Nopia",
            description = "Kue khas Banyumas dengan isian gula",
            price = 20000.0,
            stock = 15,
            img = "dummy_product"
        ),
        Product(
            id = 3,
            category_id = 1,
            category = categories[0],
            name = "Getuk Goreng",
            description = "Makanan tradisional khas Sokaraja",
            price = 18000.0,
            stock = 12,
            img = "dummy_product"
        ),
        Product(
            id = 4,
            category_id = 1,
            category = categories[0],
            name = "Tempe Kripik",
            description = "Keripik tempe renyah",
            price = 17000.0,
            stock = 25,
            img = "dummy_product"
        ),
        Product(
            id = 5,
            category_id = 1,
            category = categories[0],
            name = "Soto Sokaraja",
            description = "Soto khas Sokaraja",
            price = 20000.0,
            stock = 10,
            img = "dummy_product"
        ),
        Product(
            id = 6,
            category_id = 2,
            category = categories[1],
            name = "Es Teh",
            description = "Minuman teh dingin",
            price = 5000.0,
            stock = 30,
            img = "dummy_product"
        ),
        Product(
            id = 7,
            category_id = 2,
            category = categories[1],
            name = "Es Jeruk",
            description = "Minuman jeruk segar",
            price = 7000.0,
            stock = 25,
            img = "dummy_product"
        ),
        Product(
            id = 8,
            category_id = 2,
            category = categories[1],
            name = "Wedang Ronde",
            description = "Minuman tradisional hangat",
            price = 10000.0,
            stock = 15,
            img = "dummy_product"
        ),
        Product(
            id = 9,
            category_id = 3,
            category = categories[2],
            name = "Sapu Glagah",
            description = "Kerajinan sapu dari bahan alami",
            price = 25000.0,
            stock = 10,
            img = "dummy_product"
        ),
        Product(
            id = 10,
            category_id = 3,
            category = categories[2],
            name = "Keranjang Bambu",
            description = "Kerajinan keranjang dari bambu",
            price = 35000.0,
            stock = 8,
            img = "dummy_product"
        )
    )
}