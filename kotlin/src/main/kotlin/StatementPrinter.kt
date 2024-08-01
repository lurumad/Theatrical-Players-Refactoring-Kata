class StatementPrinter {

    fun print(invoice: Invoice, catalog: Plays): String {
        val statementTemplate = StatementTemplate()

        invoice.fill(statementTemplate, catalog)

        return statementTemplate.render()
    }

}
