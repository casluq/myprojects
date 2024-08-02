import controller.OrderController
import dataGenerators._
import entity._

import java.time.LocalDateTime

import scala.language.postfixOps

object Main {
    private lazy val orders = OrderGenerator.generate()
    private val orderController = new OrderController

    def main(args: Array[String]): Unit = {   
        val quantityOfParameters = args.size

        val validParameter = quantityOfParameters match {
            case 1 | 2 | 3 | 4 | 5 => verifyArgs(args)
            case _ => false
        }

        if (validParameter) {
            val (initialDate, finalDate) = defineDateRanges(args, quantityOfParameters)
            val filteredOrders = orderController.filterOrdersByDate(orders, initialDate, finalDate)
            val groupedOrders = orderController.groupOrdersByProductDateCreationByTimeIntervalInMonths(filteredOrders, initialDate)

            showResults(groupedOrders)
        } else {
            println("Parametros invalidos")
        }
    }

    private def defineDateRanges(args: Array[String], quantityOfParameters: Int): (LocalDateTime, LocalDateTime) = {
        quantityOfParameters match {
            case 2 =>
                val initialDate = LocalDateTime.parse(args(0).trim.replace(" ", "T"))
                val finalDate = LocalDateTime.parse(args(1).trim.replace(" ", "T"))
                (initialDate, finalDate)
            case _ =>
                val finalDate = LocalDateTime.now
                val currentYear = finalDate.getYear
                val initialDate = LocalDateTime.parse(s"$currentYear-01-01T00:00")
                (initialDate, finalDate)
        }
    }

    private def verifyArgs(parameters: Array[String]): Boolean = {
        val dateTimePattern = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}".r
        val intervalPattern = "\\d{1,2}-\\d{1,2}|>\\d{2}".r
        
        val streamOfParameters = parameters.toStream

        val hasDateTimePattern = streamOfParameters.toStream
        .map({ parameter => dateTimePattern.pattern.matcher(parameter).matches })
        .reduce((x, y) => x && y)

        val hasIntervalPattern = streamOfParameters.toStream
        .map({ parameter => intervalPattern.pattern.matcher(parameter).matches })
        .reduce((x, y) => x && y)

        hasDateTimePattern || hasIntervalPattern
    }

    private def showResults(groupedOrders: Map[String, List[Order]]): Unit = {
        println("\n--- Total of orders by intervals in months based on date of creation product---\n")
        
        groupedOrders.toStream.foreach { case (interval, orders) =>
                println(s"$interval months : ${orders.size} orders")
        }

        println("\n-------------------------------------------------------------------------------")
    }
}