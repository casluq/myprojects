import controller.OrderController
import dataGenerators.OrderGenerator
import entity.{Item, Order, Product}

import java.time.LocalDateTime

import org.scalatest.funsuite.AnyFunSuite

import scala.language.postfixOps
import scala.math.BigDecimal
import scala.util.Random

class OrderControllerTest extends AnyFunSuite {
    
    private def createFakeOrder(
        customerName: String,
        customerContact: String,
        shippingAddress: String,
        grandTotal: BigDecimal,
        createdAt: LocalDateTime,
        items: List[Item] = List.empty[Item]): Order = {
        new Order(
            customerName = customerName,
            customerContact = customerContact,
            shippingAddress = shippingAddress,
            grandTotal = grandTotal,
            createdAt = createdAt,
            items = items
        )
    }

    private def createFakeProduct(dateOfCreation: LocalDateTime = null): Product = {
        new Product(
            name = s"Product",
            category = s"Category",
            weight = generateRandomBigDecimalValue(),
            price = generateRandomBigDecimalValue(),
            createdAt = dateOfCreation)
    }

    private def generateRandomBigDecimalValue(): BigDecimal = {
        BigDecimal(new Random().nextDouble() + 0.1)
    }

    private def createLocalDateTime(
        year: Int,
        month: Int,
        day: Int,
        hour:Int = 0,
        minutes: Int = 0): LocalDateTime = {

        LocalDateTime.parse(f"$year-${month}%02d-${day}%02dT${hour}%02d:$minutes%02d")
    }

    test("Should filter orders by date") {
        val orders = 1 to 10 map { i =>

            val fakeDate = 
                if (i >= 5 && i <= 9) createLocalDateTime(2023, 6, 15)
                else createLocalDateTime(2023, i, 15)
                
            createFakeOrder(
                customerName = s"Customer Name $i",
                customerContact = s"9999-999$i",
                shippingAddress = s"Shipping adress $i",
                grandTotal = BigDecimal(new Random().nextDouble() + 0.1),
                createdAt = fakeDate)
        } toList

        val initialDate = createLocalDateTime(2023, 5, 15);
        val finalDate = createLocalDateTime(2023, 9, 27);
        
        val orderController = new OrderController

        val filteredOrders = orderController.filterOrdersByDate(orders, initialDate, finalDate)

        assert(filteredOrders.size == 5)

        filteredOrders.foreach { order => 
            assert(!order.createdAt.isBefore(initialDate) && !order.createdAt.isAfter(finalDate))  
        }
    }
    
    test("Should filter orders by product creation date using default intervals") {
        val orders = 1 to 10 map { i =>

            val fakeDate = createLocalDateTime(2023, i, 15)

            val items = 0 to 5 map { index => 
                new Item(
                    cost = BigDecimal(new Random().nextDouble() + 50),
                    shippingFee = generateRandomBigDecimalValue(),
                    taxAmount = generateRandomBigDecimalValue(),
                    product = createFakeProduct(fakeDate))  
            } toList

            createFakeOrder(
                customerName = s"Customer Name $i",
                customerContact = s"9999-999$i",
                shippingAddress = s"Shipping adress $i",
                grandTotal = generateRandomBigDecimalValue(),
                createdAt = null,
                items = items)
        } toList

        val startInterval = 1
        val endInterval = 3
        val orderController = new OrderController

        val filteredOrders = orderController.filterOrdersByProductCreationDateUsingIntervals(orders, startInterval, endInterval)

        filteredOrders.foreach { order => 
            val monthOfOldestProduct = order.items
            .map({ item => item.product.createdAt.getMonth.getValue})
            .max

            assert(monthOfOldestProduct >= startInterval && monthOfOldestProduct <= endInterval)
        }
    }
}