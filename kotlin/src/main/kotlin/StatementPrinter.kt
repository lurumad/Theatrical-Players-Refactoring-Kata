class StatementPrinter {

    fun print(invoice: Invoice): String {
        val statementTemplate = StatementTemplate(invoice.save())
        return statementTemplate.render()
    }

}
