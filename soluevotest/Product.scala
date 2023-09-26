package soluevotest

import java.math.BigDecimal
import java.time.Instant

class Product(
    val name: String,
    val category: String,
    val weight: BigDecimal,
    val price: BigDecimal,
    val createdAt: Instant) {
}

object Product {
    def apply(name: String,
            category: String,
            weight: BigDecimal,
            price: BigDecimal,
            createdAt: Instant) = {
        new Product(name = name,
            category = category,
            weight = weight,
            price = price,
            createdAt = createdAt)
    }
}