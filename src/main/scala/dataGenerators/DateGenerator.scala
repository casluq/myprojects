package dataGenerators

import java.time.LocalDateTime
import scala.util.Random

object DateGenerator extends CustomGenerator[LocalDateTime] {

    private val minimumYear = 2022
    private lazy val maximumYear = LocalDateTime.now().getYear()
    private lazy val maximumDiferenceOfYears = maximumYear - minimumYear

    override def generate(): LocalDateTime = {
        val month = new Random().nextInt(11) + 1
        val day = generateDayByMonth(month)
        val year = minimumYear + new Random().nextInt(maximumDiferenceOfYears + 1)

        LocalDateTime.parse(f"$year-${month}%02d-${day}%02dT00:00");
    }

    private def generateDayByMonth(month: Int): Int = {
        val day = new Random().nextInt(30) + 1

        if (month == 2 && day > 28) generateDayByMonth(month)
        else day
    }
}
