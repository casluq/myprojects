import dataGenerators.DateGenerator

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._
import java.time.LocalDateTime

class DateGeneratorTest extends AnyFunSuite with Matchers {

    test("Should generate a date") {
        val date = DateGenerator.generate()
        date shouldBe a [LocalDateTime]
    }
}
