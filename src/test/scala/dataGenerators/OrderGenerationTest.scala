import dataGenerators.OrderGenerator

import java.time.LocalDateTime

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._

class OrderGenerationTest extends AnyFunSuite with Matchers {
  
    test("Should generate a list of orders") {
        val orders = OrderGenerator.generate()

        assert(orders.size > 0)
        
        orders.foreach { order => 
            assert(order.items.size > 0)
            assert(order.customerName.length > 0)
            assert(order.customerContact.length > 0)
            assert(order.shippingAddress.length > 0)
            assert(order.grandTotal.doubleValue > 0.0)    

            order.createdAt shouldBe a [LocalDateTime]
        }
    }
}
