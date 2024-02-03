import java.text.NumberFormat
import java.util.Locale

class StatementPrinter {

    fun print(invoice: Invoice, catalog: PlayCatalog): String {
        var invoiceAmount = Amount(0)
        var invoiceCredits = Credits(0)
        var result = "Statement for ${invoice.customer}\n"

        val format = { number: Long ->  NumberFormat.getCurrencyInstance(Locale.US).format(number)}

        invoice.performances.forEach { performance ->
            val play = catalog.playBy(performance.playID)

            invoiceCredits = invoiceCredits.add(performance.credits(play))
            invoiceAmount = invoiceAmount.add(performance.amount(play))

            result += "  ${play.name}: ${format((performance.amount(play).usd()).toLong())} (${performance.audience} seats)\n"
        }
        result += "Amount owed is ${format((invoiceAmount.usd()).toLong())}\n"
        result += "You earned $invoiceCredits credits\n"
        return result
    }













}
