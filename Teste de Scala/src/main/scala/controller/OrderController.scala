package controller

import entity.Order

import java.time.LocalDateTime

import scala.language.postfixOps

class OrderController {

    def filterOrdersByDate(
        orders: List[Order],
        initialDate: LocalDateTime,
        finalDate: LocalDateTime): List[Order] = {

        orders
        .toStream
        .filter(o => !o.createdAt.isBefore(initialDate) && !o.createdAt.isAfter(finalDate))
        .toList
    }

    def groupOrdersByProductDateCreationByTimeIntervalInMonths(
        orders: List[Order],
        filterStartDate: LocalDateTime): Map[String, List[Order]] = {

        val standardMonthRanges = List(
            List(1, 3),
            List(4, 6),
            List(7, 9),
            List(10, 12),
            List(12))

        val yearOfFilterStartDate = filterStartDate.getYear

        val (oldestOrders, ordersInsideFilterInterval) = orders.partition { order => 
            val oldestProductYear = order.items.toStream
            .map({ item => item.product.createdAt.getYear })
            .min

            oldestProductYear < yearOfFilterStartDate
        }

        standardMonthRanges.toStream.map { intervals => 
            val startInterval = intervals.head
            val endInterval = intervals.last

            val filteredOrders = filterOrdersByProductCreationDateUsingIntervals(ordersInsideFilterInterval, startInterval, endInterval)
     
            if (startInterval != endInterval) s"${startInterval}-$endInterval" -> filteredOrders
            else ">12" -> oldestOrders
            
        } toMap 
    }

    def filterOrdersByProductCreationDateUsingIntervals(
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
