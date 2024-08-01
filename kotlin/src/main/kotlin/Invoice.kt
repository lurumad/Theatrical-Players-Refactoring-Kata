import memento.InvoiceMemento
import memento.State

data class Invoice(
    private val customer: String,
    private val performances: PerformanceList
): State<InvoiceMemento> {
    // We don't want to make premature decisions about the performance.
    // If we do, we can look for a way to avoid it. For instance, memoization.
    private fun credits(): Credits {
        var invoiceCredits = Credits(0)
        performances.forEach { performance ->
            invoiceCredits = invoiceCredits.add(performance.credits())
        }
        return invoiceCredits
    }

    private fun amount(): Amount {
        var invoiceAmount = Amount(0)
        performances.forEach { performance ->
            invoiceAmount = invoiceAmount.add(performance.amount())
        }
        return invoiceAmount
    }

    override fun save(): InvoiceMemento {
        return InvoiceMemento(
            customer,
            amount().save().amount,
            credits().save().credits,
            performances.map { it.save() }
        )
    }
}
