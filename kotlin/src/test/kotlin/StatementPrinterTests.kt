
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.approvaltests.Approvals.verify

class StatementPrinterTests {

    @Test
    internal fun exampleStatement() {
        val performanceList = PerformanceList(
            listOf(
                Performance(Play("Hamlet", "tragedy"), 55),
                Performance(Play("As You Like It", "comedy"), 35),
                Performance(Play("Othello", "tragedy"), 40)
            )
        )
        val invoice = Invoice("BigCo", performanceList)
        val statementPrinter = StatementPrinter()

        val result = statementPrinter.print(invoice)

        verify(result)
    }

    @Test
    internal fun statementWithNewPlayTypes() {
        val performanceList = PerformanceList(
            listOf(
                Performance(Play("Henry V", "history"), 53),
                Performance(Play("As You Like It", "pastoral"), 55)
            )
        )
        val invoice = Invoice("BigCo", performanceList)
        val statementPrinter = StatementPrinter()
        
        assertThrows(Error::class.java) { statementPrinter.print(invoice) }
    }
}
