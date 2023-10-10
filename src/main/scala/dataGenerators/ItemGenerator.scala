package dataGenerators

import entity.Item

import scala.language.postfixOps
import scala.math.BigDecimal.RoundingMode
import scala.util.Random

object ItemGenerator extends CustomGenerator[List[Item]] {
    private lazy val products = ProductGenerator.generate()
    private val randomNumberProvider = new Random()

    override def generate ():List[Item] = {
       products
        .toStream
        .map { p =>
            val fee = BigDecimal(randomNumberProvider.nextDouble()).setScale(2, RoundingMode.UP)
            val tax = BigDecimal(randomNumberProvider.nextDouble()).setScale(2, RoundingMode.UP)

            new Item(
                cost = randomNumberProvider.nextInt(100) + 1,
                shippingFee = fee,
                taxAmount = tax,
                product = p)
        } toList
    }
}
