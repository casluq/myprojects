package entity

import scala.math.BigDecimal

class Item(
    val cost: BigDecimal,
    val shippingFee: BigDecimal,
    val taxAmount: BigDecimal,
    val product: Product) {
}
