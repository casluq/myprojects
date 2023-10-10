import dataGenerators.ProductGenerator

import java.time.LocalDateTime
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._

class ProductGeneratorTest extends AnyFunSuite with Matchers {

    test("Should generate a list of product") {
        lazy val products = ProductGenerator.generate()

        assert(products.size > 0)

        products.foreach { product => 

            assert(product.name.length > 0)
            assert(product.category.length > 0)
            assert(product.weight.doubleValue > 0.0)
            assert(product.price.doubleValue > 0.0)
            
            product.createdAt shouldBe a [LocalDateTime]
        }
    }
}
