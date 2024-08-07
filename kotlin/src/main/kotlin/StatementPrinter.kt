class StatementPrinter {

    fun print(invoice: Invoice): String {
        val statementTemplate = StatementTemplate(invoice.state())
        return statementTemplate.render()
    }

}
