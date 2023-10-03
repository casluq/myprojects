package dataGenerators

import entity.Product
import scala.util.Random
import scala.math.BigDecimal

object ProductGenerator extends CustomGenerator[List[Product]] {

    private val categories = List("Smart Phone", "TV", "Shoes", "Glasses")
    private val productsNameByCategory = Map(
        "Smart Phone" -> List(
            "Iphone 8",
            "Iphone 8 Plus",
            "Iphone 12 Pro",
            "Iphone 12 Pro Max",
            "Iphone 13 Pro",
            "Iphone 13 Pro Max",
            "Iphone 14 Pro",
            "Iphone 14 Pro Max",
            "Iphone 15 Pro",
            "Iphone 15 Pro Max",
            "Xiaomi 8",
            "Xiaomi 9",
            "Xiaomi 10",
            "Xiaomi 11",
            "Xiaomi 12",
            "Xiaomi 13",
            "Samsung Galaxy S12",
            "Samsung Galaxy S13",
            "Samsung Galaxy S14",
            "Samsung Galaxy S15",
            "Samsung Galaxy S16",
            "Samsung Galaxy S17",
            "Samsung Galaxy S18",
            "Samsung Galaxy S19",
            "Samsung Galaxy S20",
            "Samsung Galaxy S21",
            "Samsung Galaxy S22",
            "Black Berry",
            "Black Berry 2",
            "Black Berry 3",
            "Black Berry 4"),
        "TV" -> List (
            "Samsung 42",
            "Samsung 56",
            "Samsung 72",
            "LG 42 LED Full HD",
            "LG 56 LED Full HD",
            "LG 72 LED Full HD",
            "TV 42 LED 4k 60FPS",
            "TV 56 LED 4k 60FPS",
            "TV 72 LED 4k 60FPS"),
        "Shoes" -> List(
            "Nike SB", 
            "Nike SB Azul", 
            "Nike SB Branco", 
            "SB Shoes",
            "Friday",
            "Drop Dead",
            "DC",
            "Vans",
            "Lakai",
            "Adidas", 
            "Mizuno"), 
        "Glasses" -> List(
            "Juliet padrão",
            "Juliet Preto Fosco",
            "Juliet Espelhado",
            "Ray-ban", 
            "Sun Glasses", 
            "Evoke"))

    override def generate(): List[Product] = {
        productsNameByCategory
        .toStream
        .zipWithIndex
        .flatMap { case ((category, names), i) => 
            names.map { name => 
                new Product(
                    name = name,
                    category = category,
                    weight = BigDecimal(new Random().nextDouble() + 0.1),
                    price = BigDecimal(new Random().nextInt(50) + 1),
                    createdAt = DateGenerator.generate())
            }     
        } toList
    }
}
