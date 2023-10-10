import dataGenerators.ItemGenerator

import entity.Product

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._

import scala.language.postfixOps

class ItemGeneratorTest extends AnyFunSuite with Matchers {

    test("Should generate a list o Items") {
        val items = ItemGenerator.generate()

        assert(items.size > 0)

        items.foreach { item => 
            assert(item.cost.doubleValue > 0.0)
            assert(item.shippingFee.doubleValue > 0.00)
            assert(item.taxAmount.doubleValue > 0.0)

            item.product shouldBe a [Product]
        }
    }
}
