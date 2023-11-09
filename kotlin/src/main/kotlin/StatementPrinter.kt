import java.text.NumberFormat
import java.util.Locale
import kotlin.math.floor
import kotlin.math.max

class StatementPrinter {

    fun print(invoice: Invoice, plays: Map<String, Play>): String {
        var invoiceAmount = Amount(0)
        var invoiceCredits = Credits(0)
        var result = "Statement for ${invoice.customer}\n"

        val format = { number: Long ->  NumberFormat.getCurrencyInstance(Locale.US).format(number)}

        invoice.performances.forEach { performance ->
            val play = plays.getValue(performance.playID)
            val performanceAmount: Amount = performanceAmount(performance, play)
            val performanceCredits = performanceCredits(performance, play)

            invoiceCredits = invoiceCredits.add(performanceCredits)
            invoiceAmount = invoiceAmount.add(performanceAmount)

            result += "  ${play.name}: ${format((performanceAmount.usd()).toLong())} (${performance.audience} seats)\n"
        }
        result += "Amount owed is ${format((invoiceAmount.usd()).toLong())}\n"
        result += "You earned $invoiceCredits credits\n"
        return result
    }

    private fun performanceCredits(performance: Performance, play: Play): Credits {
        var performanceCredits = Credits(max(performance.audience - 30, 0))
        performanceCredits = performanceCredits.add(performanceCreditsByGenre(performance, play))
        return performanceCredits
    }

    private fun performanceCreditsByGenre(performance: Performance, play: Play) = if ("comedy" == play.type) {
        Credits(floor((performance.audience / 5).toDouble()).toInt())
    } else {
        Credits(0)
    }

    private fun performanceAmount(perf: Performance, play: Play): Amount {
        var performanceAmount: Amount

        when (play.type) {
            "tragedy" -> {
                performanceAmount = Amount(40000)
                performanceAmount = performanceAmount.add(tragedyExtraAmountByAudience(perf))
                performanceAmount = performanceAmount.add(tragedyExtraAmountByGenre(perf))
            }

            "comedy" -> {
                performanceAmount = Amount(30000)
                performanceAmount = performanceAmount.add(comedyExtraAmountByAudience(perf))
                performanceAmount = performanceAmount.add(comedyExtraAmountByGenre(perf))
            }

            else -> throw Error("unknown type: {play.type}")
        }
        return performanceAmount
    }

    private fun tragedyExtraAmountByGenre(perf: Performance): Amount {
        return Amount(0)
    }

    private fun comedyExtraAmountByGenre(perf: Performance) = Amount(300 * perf.audience)

    private fun comedyExtraAmountByAudience(perf: Performance) = if (perf.audience > 20) {
        Amount(10000 + 500 * (perf.audience - 20))
    } else {
        Amount(0)
    }

    private fun tragedyExtraAmountByAudience(perf: Performance) = if (perf.audience > 30) {
        Amount(1000 * (perf.audience - 30))
    } else {
        Amount(0)
    }
}
