package entity

import scala.math.BigDecimal
import java.time.LocalDateTime

class Order(
    val customerName: String,
    val customerContact: String,
    val shippingAddress: String,
    val grandTotal: BigDecimal,
    val createdAt: LocalDateTime,
    val items: List[Item]) {
}
