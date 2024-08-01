import kotlin.math.floor
import kotlin.math.max

data class Performance(
    private val playID: String,
    private val audience: Int
) {

    fun credits(plays: Plays): Credits {
        val play = plays.playBy(playID)
        var performanceCredits = Credits(max(audience - 30, 0))
        performanceCredits = performanceCredits.add(creditsByGenre(play))
        return performanceCredits
    }

    fun amount(plays: Plays): Amount {
        val play = plays.playBy(playID)
        if (play.type == "tragedy") {
            return amountByTragedy()
        }

        if (play.type == "comedy") {
            return amountByComedy()
        }

        throw Error("unknown type: {play.type}")
    }

    private fun amountByComedy(): Amount {
        var performanceAmount = Amount(30000)
        performanceAmount = performanceAmount.add(comedyExtraAmountByAudience())
        return performanceAmount.add(comedyExtraAmountByGenre())
    }

    private fun amountByTragedy(): Amount {
        var performanceAmount = Amount(40000)
        performanceAmount = performanceAmount.add(extraAmountByAudience())
        return performanceAmount.add(tragedyExtraAmountByGenre())
    }

    private fun extraAmountByAudience(): Amount {
        if (audience > 30) {
            return Amount(1000 * (audience - 30))
        }
        return Amount(0)
    }

    private fun comedyExtraAmountByAudience(): Amount {
        if (audience > 20) {
            return Amount(10000 + 500 * (audience - 20))
        }
        return Amount(0)
    }

    private fun comedyExtraAmountByGenre() = Amount(300 * audience)

    private fun tragedyExtraAmountByGenre(): Amount {
        return Amount(0)
    }

    private fun creditsByGenre(play: Play): Credits {
        if ("comedy" == play.type) {
            return Credits(floor((audience / 5).toDouble()).toInt())
        }
        return Credits(0)
    }

    fun fill(template: StatementTemplate, plays: Plays) {
        val play = plays.playBy(playID)
        template.line(play.name, amount(plays), audience)
    }
}
