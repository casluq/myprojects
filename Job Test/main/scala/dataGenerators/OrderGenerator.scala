package dataGenerators

import java.time.LocalDate
import scala.util.Random
import entity.Item
import entity.Order

object OrderGenerator extends CustomGenerator[List[Order]] {
  
    private lazy val allItems = ItemGenerator.generate()
    private val customerNames = List(
        "Jonas",
        "James",
        "Joan",
        "Hilda",
        "Andrew",
        "Amanda",
        "Robert",
        "Amelia",
        "Charlotte",
        "Dani",
        "Gisele",
        "Aline",
        "Kaya",
        "Dayna",
        "Amelie",
        "Denis",
        "Deborah",
        "Nina")

    private val addresses = List(
        "Rua Mareixal Floriano, 2220",
        "Rua Saldanha Marinho, 2345",
        "Residêncial Mercúrio, 2390 apt 345",
        "Conjunto Residêncial Belo Rio, 987 apt 45",
        "Conjunto Mineiros, casa 87")

    private val contacts = List(
        "91234-6842",
        "94567-4827",
        "95678-4987",
        "98910-4520",
        "91124-3687",
        "96657-9274",
        "96287-6354",
        "99875-2301",
        "92541-2487",
        "91587-6874",
        "91947-9854",
        "91867-5478",
        "92496-0123",
        "92346-3210",
        "93496-3333",
        "99754-2222",
        "99999-1111",
        "98765-4321",
    )

    override def generate(): List[Order] = {
        allItems
        .toStream
        .map { _ => 
            new Order(
                customerName = customerNames(new Random().nextInt(customerNames.size - 1)),
                customerContact = contacts(new Random().nextInt(contacts.size - 1)),
                shippingAddress = addresses(new Random().nextInt(addresses.size - 1)),
                grandTotal = new Random().nextInt(200) + 1,
                createdAt = DateGenerator.generate(),
                items = defineOrderItems())
        } toList
    }

    private def defineOrderItems(): List[Item] = {
        val quantityOfItems = new Random().nextInt(5) + 1

        0 to quantityOfItems map { q =>
            val randomIndex = new Random().nextInt(allItems.size) match {
                case number if (number == 0) => 1
                case number if (number > 0) => number
            }
            
            allItems(randomIndex)
        } toList
    }
}
