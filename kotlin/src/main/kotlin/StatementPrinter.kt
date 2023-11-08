import java.text.NumberFormat
import java.util.Locale
import kotlin.math.floor
import kotlin.math.max

class StatementPrinter {

    fun print(invoice: Invoice, plays: Map<String, Play>): String {
        var totalAmount = 0
        var credits = Credits(0)
        var result = "Statement for ${invoice.customer}\n"

        val format = { number: Long ->  NumberFormat.getCurrencyInstance(Locale.US).format(number)}

        invoice.performances.forEach { perf ->
            val play = plays.getValue(perf.playID)
            var performanceAmount: Amount

            when (play.type) {
                "tragedy" -> {
                    performanceAmount = Amount(40000)
                    if (perf.audience > 30) {
                        val performanceAmountByAudience = Amount(1000 * (perf.audience - 30))
                        performanceAmount = performanceAmount.add(performanceAmountByAudience)
                    }
                }
                "comedy" -> {
                    performanceAmount = Amount(30000)
                    if (perf.audience > 20) {
                        val performanceAmountByAudience = Amount(10000 + 500 * (perf.audience - 20))
                        performanceAmount = performanceAmount.add(performanceAmountByAudience)
                    }
                    val performanceAmountByType = Amount(300 * perf.audience)
                    performanceAmount = performanceAmount.add(performanceAmountByType)
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

            totalAmount += performanceAmount.amount
        }
        result += "Amount owed is ${format((totalAmount / 100).toLong())}\n"
        result += "You earned $credits credits\n"
        return result
    }

}
