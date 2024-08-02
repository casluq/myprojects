package entity

import scala.math.BigDecimal
import java.time.LocalDateTime

class Product(
    val name: String,
    val category: String,
    val weight: BigDecimal,
    val price: BigDecimal,
    val createdAt: LocalDateTime) {
}