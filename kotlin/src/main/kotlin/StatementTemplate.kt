import memento.InvoiceMemento
import java.text.NumberFormat
import java.util.*

class StatementTemplate(private val invoice: InvoiceMemento) {
    private val format: (Long) -> String =
        { number: Long -> NumberFormat.getCurrencyInstance(Locale.US).format(number) }

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
