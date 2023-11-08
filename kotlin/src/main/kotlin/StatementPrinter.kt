import java.text.NumberFormat
import java.util.Locale
import kotlin.math.floor
import kotlin.math.max

class StatementPrinter {

    fun print(invoice: Invoice, plays: Map<String, Play>): String {
        var invoiceAmount = Amount(0)
        var credits = Credits(0)
        var result = "Statement for ${invoice.customer}\n"

        val format = { number: Long ->  NumberFormat.getCurrencyInstance(Locale.US).format(number)}

        invoice.performances.forEach { perf ->
            val play = plays.getValue(perf.playID)
            var performanceAmount: Amount

            when (play.type) {
                "tragedy" -> {
                    performanceAmount = Amount(40000)
                    val extraAmountByAudience = extraAmountByAudience(perf)
                    performanceAmount = performanceAmount.add(extraAmountByAudience)
                }
                "comedy" -> {
                    performanceAmount = Amount(30000)
                    if (perf.audience > 20) {
                        val extraAmountByAudience = Amount(10000 + 500 * (perf.audience - 20))
                        performanceAmount = performanceAmount.add(extraAmountByAudience)
                    }
                    val extraAmountByType = Amount(300 * perf.audience)
                    performanceAmount = performanceAmount.add(extraAmountByType)
                }
                else -> throw Error("unknown type: {play.type}")
            }

            // add volume credits
            val performanceCredits = Credits(max(perf.audience - 30, 0))
            credits = credits.add(performanceCredits)

            // add extra credit for every ten comedy attendees
            if ("comedy" == play.type) {
                val extraPerformanceCreditsByType = Credits(floor((perf.audience / 5).toDouble()).toInt())
                credits = credits.add(extraPerformanceCreditsByType)
            }

            // print line for this order
            result += "  ${play.name}: ${format((performanceAmount.usd()).toLong())} (${perf.audience} seats)\n"

            invoiceAmount = invoiceAmount.add(performanceAmount)
        }
        result += "Amount owed is ${format((invoiceAmount.usd()).toLong())}\n"
        result += "You earned $credits credits\n"
        return result
    }

    private fun extraAmountByAudience(perf: Performance) = if (perf.audience > 30) {
        Amount(1000 * (perf.audience - 30))
    } else {
        Amount(0)
    }
}
