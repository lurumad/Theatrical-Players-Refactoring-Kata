data class Invoice(
    private val customer: String,
    private val performances: PerformanceList
) {
    fun fill(template: StatementTemplate, plays: Plays) {
        var invoiceAmount = Amount(0)
        var invoiceCredits = Credits(0)
        template.customer(customer)

        performances.forEach { performance ->
            val play = plays.playBy(performance.playID)

            invoiceCredits = invoiceCredits.add(performance.credits(play))
            invoiceAmount = invoiceAmount.add(performance.amount(play))

            template.line(play.name, performance.amount(play), performance.audience)
        }
        template.amount(invoiceAmount)
        template.credits(invoiceCredits)
    }

}
