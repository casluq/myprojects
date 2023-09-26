import soluevotest._

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

import scala.io.StdIn.readLine
import scala.language.postfixOps
import java.time.temporal.ChronoField

object Teste {
    
    def main(args: Array[String]) = {

        lazy val products = createProducts()
        lazy val items = createItems(products)
        lazy val orders = createOrders(items)

        val filteredOrders = args.size match {
            // case 1 => 
            //     val parameter = args.head
                
            //     verifyArgs(parameter)
            //     getOrdersByFilter(parameter, orders)
            case 2 => 
                // verifyArgs(args)
                val (initialDate, finalDate) = getDatesForFilter(args)
                getOrdersByFilter(initialDate, finalDate, orders)
            // case _ => 
        }

        if (filteredOrders.isEmpty) {
            println("\nNenhuma ordem foi encontrada para o período informado.")
            println(s"Período: ${args.head} à ${args.last}")
        } else {
            filteredOrders
                .groupBy({ order => order.items.head.product.createdAt})
                .foldLeft(Map.empty[String, List[Order]]) 
                    { case (ordersByInterval, (date, orders)) => 

                        val adjustedDate = LocalDateTime.from(date.atZone(ZoneId.systemDefault()))
                        val month = adjustedDate.getMonth().getValue()

                        month match {
                            case 1 | 2 | 3 => 
                                val interval = "1-3 months"
                                
                                if (ordersByInterval.contains(interval)){
                                    val updatedList  = ordersByInterval(interval) ++ orders
                                    ordersByInterval ++ Map(interval -> updatedList)
                                } else {
                                    ordersByInterval ++ Map(interval -> orders)
                                }

                            case 4 | 5 | 6 => 
                                val interval = "4-6 months"

                                if (ordersByInterval.contains(interval)){
                                    val updatedList  = ordersByInterval(interval) ++ orders
                                    ordersByInterval ++ Map(interval -> updatedList)
                                } else {
                                    ordersByInterval ++ Map(interval -> orders)
                                }

                            case 7 | 8 | 9 => 
                                val interval = "7-9 months"

                               if (ordersByInterval.contains(interval)){
                                   val updatedList  = ordersByInterval(interval) ++ orders
                                   ordersByInterval ++ Map(interval -> updatedList)
                                } else {
                                    ordersByInterval ++ Map(interval -> orders)
                                }

                            case 10 | 11 | 12 => 
                                val interval = "10-12 months: "

                                if (ordersByInterval.contains(interval)){
                                    val updatedList  = ordersByInterval(interval) ++ orders
                                    ordersByInterval ++ Map(interval -> updatedList)
                                } else {
                                    ordersByInterval ++ Map(interval -> orders)
                                }
                        }
                    }
                    .foreach { case (interval, orders) =>
                        println(interval + " -> " + orders.size)
                    }
        }
    }

    // def verifyArgs(args: Array[String]): Unit = {
    //     args.foreach(verifyArgs)
    // }

    // def verifyArgs(parameter: String): Unit = {
    //     val parametersPattern = "\\d > \\d|\\d{4}-\\d{2}-\\d{2}".r
    // }

    def getDatesForFilter(args: Array[String]) = {
        val adjustedParameters = args.map(_.trim.replace(" ", "T"))

        val initialDate = LocalDateTime
        .parse(adjustedParameters.head)
        .atZone(ZoneId.systemDefault())
        .toInstant()

        val finalDate = LocalDateTime
        .parse(adjustedParameters.last)
        .atZone(ZoneId.systemDefault())
        .toInstant()

        (initialDate, finalDate)
    }

    def createOrders(items: List[Item]): List[Order] = {
        items
        .zipWithIndex
        .map { case (item, i) => 
            val sequential = i + 1
            val createdAt = Instant.parse(f"2023-${sequential}%02d-01T18:00:00.00Z")

            val adjustedShippingFee = item.shippingFee.add(new BigDecimal(1))
            val ajustedTaxAmount = item.taxAmount.add(new BigDecimal(1))

            val totalCost = item.cost
                .multiply(ajustedTaxAmount)
                .multiply(adjustedShippingFee)
  
            Order(
                customerName = s"Order name $i",
                customerContact = f"9999988${i}%02d",
                shippingAddress = "Adress number $i",
                grandTotal = totalCost,
                createdAt = createdAt,
                items = List(item))
        }
    }

    def createItems(products: List[Product]): List[Item] = {
        products
        .zipWithIndex
        .map { case (product, i) => 
            Item(
                cost = new BigDecimal(i + products.size),
                shippingFee = new BigDecimal(s"0.$i"),
                taxAmount = new BigDecimal(s"0.$i"),
                product = product)
        }
    }

    def createProducts(): List[Product] = {
        1 to 12 map { i => 
            val data = Instant.parse(f"2023-${i}%02d-01T16:00:00.00Z")

            Product(
                name = s"product $i",
                category = s"category $i",
                weight = new BigDecimal(i),
                price = new BigDecimal(i),
                createdAt = data)
        } toList
    }

    def getOrdersByFilter(initialDate:Instant,
                            finalDate: Instant,
                            orders: List[Order]): List[Order] = {
        orders.filter { order => 
            !order.createdAt.isBefore(initialDate) &&
            !order.createdAt.isAfter(finalDate)
        }
    }

    // def getOrdersByFilter(args: String): List[Order] = {
    // }
}