data class Invoice(
    private val customer: String,
    private val performances: PerformanceList
) {
    fun fill(template: StatementTemplate, plays: Plays) {
        template.customer(customer)

        performances.forEach { performance ->
            val play = plays.playBy(performance.playID)
            performance.fill(template, play)

        }

        template.amount(amount(plays))
        template.credits(credits(plays))
    }

    // We don't want to make premature decisions about the performance.
    // If we do, we can look for a way to avoid it. For instance, memoization.
    private fun credits(plays: Plays): Credits {
        var invoiceCredits = Credits(0)
        performances.forEach { performance ->
            val play = plays.playBy(performance.playID)
            invoiceCredits = invoiceCredits.add(performance.credits(play))
        }
        return invoiceCredits
    }

    private fun amount(plays: Plays): Amount {
        var invoiceAmount = Amount(0)
        performances.forEach { performance ->
            val play = plays.playBy(performance.playID)
            invoiceAmount = invoiceAmount.add(performance.amount(play))
        }
        return invoiceAmount
    }
}
