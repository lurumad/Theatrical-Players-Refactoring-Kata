import memento.InvoiceMemento
import java.text.NumberFormat
import java.util.*

class StatementTemplate(private val invoice: InvoiceMemento) {
    private val format: (Long) -> String =
        { number: Long -> NumberFormat.getCurrencyInstance(Locale.US).format(number) }
    private var customer: String = ""
    private var amount: Amount = Amount(0)
    private var credits: Credits = Credits(0)
    private val lines = mutableListOf<Line>()

    fun customer(customer: String) {
        this.customer = customer
    }

    fun amount(amount: Amount) {
        this.amount = amount
    }

    fun credits(credits: Credits) {
        this.credits = credits
    }

    fun line(playName: String, amount: Amount, audience: Int) {
        lines.add(Line(playName, amount, audience))
    }

    data class Line(val playName: String, val amount: Amount, val audience: Int)

    fun render(): String {
        var statement = "Statement for ${invoice.customer}\n"
        invoice.performances.forEach { performance ->
            statement += "  ${performance.playName}: ${formatAmount(performance.amount)} (${performance.audience} seats)\n"
        }
        statement += "Amount owed is ${formatAmount(invoice.amount)}\n"
        statement += "You earned ${invoice.credits} credits\n"
        return statement
    }

    private fun formatAmount(performanceAmount: Int) = format(performanceAmount.toLong())

}
