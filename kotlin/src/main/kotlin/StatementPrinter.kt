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
            var thisAmount = 0
            var performanceAmount = Amount()

            when (play.type) {
                "tragedy" -> {
                    thisAmount = 40000
                    performanceAmount = Amount(40000)
                    if (perf.audience > 30) {
                        val performanceAmountByAudience = 1000 * (perf.audience - 30)
                        thisAmount += performanceAmountByAudience
                        performanceAmount = performanceAmount.add(Amount(thisAmount))
                    }
                }
                "comedy" -> {
                    thisAmount = 30000
                    performanceAmount = Amount(30000)
                    if (perf.audience > 20) {
                        val performanceAmountByAudience = 10000 + 500 * (perf.audience - 20)
                        thisAmount += performanceAmountByAudience
                        performanceAmount = performanceAmount.add(Amount(thisAmount))
                    }
                    val performanceAmountByType = 300 * perf.audience
                    thisAmount += performanceAmountByType
                    performanceAmount = performanceAmount.add(Amount(thisAmount))
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
            result += "  ${play.name}: ${format((thisAmount / 100).toLong())} (${perf.audience} seats)\n"

            totalAmount += thisAmount
        }
        result += "Amount owed is ${format((totalAmount / 100).toLong())}\n"
        result += "You earned $credits credits\n"
        return result
    }

}
