package soluevotest

import soluevotest.Item
import java.math.BigDecimal
import java.time.Instant

class Order(
    val customerName: String,
    val customerContact: String,
    val shippingAddress: String,
    val grandTotal: BigDecimal,
    val createdAt: Instant,
    val items: List[Item]) {
}

object Order {
    def apply(customerName: String,
                customerContact: String,
                shippingAddress: String,
                grandTotal: BigDecimal,
                createdAt: Instant,
                items: List[Item]) = {
        new Order(
            customerName,
            customerContact,
            shippingAddress,
            grandTotal,
            createdAt,
            items)
    }
}