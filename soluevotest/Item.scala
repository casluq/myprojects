package soluevotest

import soluevotest.Product
import java.math.BigDecimal

class Item(
    val cost: BigDecimal,
    val shippingFee: BigDecimal,
    val taxAmount: BigDecimal,
    val product: Product) {
}

object Item {
    def apply(cost: BigDecimal,
            shippingFee: BigDecimal,
            taxAmount: BigDecimal,
            product: Product) = {

            new Item(cost = cost,
                    shippingFee = shippingFee,
                    taxAmount = taxAmount,
                    product = product)
    }
}