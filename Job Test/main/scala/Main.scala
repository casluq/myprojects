import java.time.LocalDateTime

import entity._
import dataGenerators._

object Main {
    lazy val orders = OrderGenerator.generate()
    val defaultIntervals = List(
        List(1, 3),
        List(4, 6),
        List(7, 9),
        List(10, 12),
        List(12))

    def main(args: Array[String]): Unit = {   
        val quantityOfParameters = args.size

        val validParameter = quantityOfParameters match {
            case 1 | 2 | 3 | 4 | 5 => verifyArgs(args)
            case _ => false
        }

        if (validParameter) {
            val groupedOrders = quantityOfParameters match {
                case 2 => 
                    val initialDate = LocalDateTime.parse(args(0).trim.replace(" ", "T"))
                    val finalDate = LocalDateTime.parse(args(1).trim.replace(" ", "T"))

                    val filteredOrders = filterOrdersByDate(args, initialDate, finalDate)
                    groupOrdersByProductDateCreationByTimeIntervalInMonths(filteredOrders, initialDate)

                case _ => 
                    val finalDate = LocalDateTime.now
                    val currentYear = finalDate.getYear
                    val initialDate = LocalDateTime.parse(s"$currentYear-01-01T00:00")
                    val filteredOrders = filterOrdersByDate(args, initialDate, finalDate)

                    groupOrdersByProductDateCreationByTimeIntervalInMonths(filteredOrders, initialDate)
            }
            
            println("\n--- Total of orders by intervals in months based on date of creation product---\n")

            groupedOrders.toStream.foreach { case (interval, orders) =>
                 println(s"$interval months : ${orders.size} orders")
            }

            println("\n-------------------------------------------------------------------------------")

        } else {
            println("Parametros invalidos")
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

    private def filterOrdersByDate(
        args: Array[String],
        initialDate: LocalDateTime,
        finalDate: LocalDateTime): List[Order] = {

        orders
        .toStream
        .filter(o => !o.createdAt.isBefore(initialDate) && !o.createdAt.isAfter(finalDate))
        .toList
    }

    private def groupOrdersByProductDateCreationByTimeIntervalInMonths(
        orders: List[Order],
        filterStartDate: LocalDateTime): Map[String, List[Order]] = {

        val yearOfFilterStartDate = filterStartDate.getYear

        val (oldestOrders, ordersInsideFilterInterval) = orders.partition { order => 
            val oldestProductYear = order.items.toStream
            .map({ item => item.product.createdAt.getYear })
            .min

            oldestProductYear < yearOfFilterStartDate
        }

        defaultIntervals.toStream.map { intervals => 
            val startInterval = intervals.head
            val endInterval = intervals.last

            val filteredOrders = filterOrdersByProductCreationDateUsingIntervals(ordersInsideFilterInterval, startInterval, endInterval)
     
            if (startInterval != endInterval) s"${startInterval}-$endInterval" -> filteredOrders
            else ">12" -> oldestOrders
            
        } toMap 
    }

    private def filterOrdersByProductCreationDateUsingIntervals(
        orders: List[Order],
        startInterval: Int,
        endInterval: Int): List[Order] = {

        orders.toStream.filter { order => 
            
            val monthOfOldestProduct = order.items.toStream
            .map({ item => item.product.createdAt.getMonth.getValue })
            .max

            monthOfOldestProduct >= startInterval && monthOfOldestProduct <= endInterval
        } toList
    }
}