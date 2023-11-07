import java.text.NumberFormat
import java.util.Locale
import kotlin.math.floor
import kotlin.math.max

class StatementPrinter {

    fun print(invoice: Invoice, plays: Map<String, Play>): String {
        var totalAmount = 0
        var volumeCredits = 0
        var credits = Credits(volumeCredits)
        var result = "Statement for ${invoice.customer}\n"

        val format = { number: Long ->  NumberFormat.getCurrencyInstance(Locale.US).format(number)}

        invoice.performances.forEach { perf ->
            val play = plays.getValue(perf.playID)
            var thisAmount = 0

            when (play.type) {
                "tragedy" -> {
                    thisAmount = 40000
                    if (perf.audience > 30) {
                        thisAmount += 1000 * (perf.audience - 30)
                    }
                }
                "comedy" -> {
                    thisAmount = 30000
                    if (perf.audience > 20) {
                        thisAmount += 10000 + 500 * (perf.audience - 20)
                    }
                    thisAmount += 300 * perf.audience
                }
                else -> throw Error("unknown type: {play.type}")
            }

            // add volume credits
            val performanceCredits = max(perf.audience - 30, 0)
            volumeCredits += performanceCredits

            credits = credits.add(Credits(performanceCredits))

            // add extra credit for every ten comedy attendees
            if ("comedy" == play.type) {
                val performanceCreditsByType = floor((perf.audience / 5).toDouble()).toInt()
                volumeCredits += performanceCreditsByType

                credits = credits.add(Credits(performanceCreditsByType))
            }

            // print line for this order
            result += "  ${play.name}: ${format((thisAmount / 100).toLong())} (${perf.audience} seats)\n"

            totalAmount += thisAmount
        }
        result += "Amount owed is ${format((totalAmount / 100).toLong())}\n"
        result += "You earned $volumeCredits credits\n"
        return result
    }

}
