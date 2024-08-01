
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.approvaltests.Approvals.verify

class StatementPrinterTests {

    @Test
    internal fun exampleStatement() {
        val catalog = Plays(
            mapOf(
                "hamlet" to Play("Hamlet", "tragedy"),
                "as-like" to Play("As You Like It", "comedy"),
                "othello" to Play("Othello", "tragedy")
            )
        )
        val performanceList = PerformanceList(
            listOf(
                Performance("hamlet", 55),
                Performance("as-like", 35),
                Performance("othello", 40)
            )
        )
        val invoice = Invoice("BigCo", performanceList)
        val statementPrinter = StatementPrinter()

        val result = statementPrinter.print(invoice, catalog)

        verify(result)
    }

    @Test
    internal fun statementWithNewPlayTypes() {
        val catalog = Plays(
            mapOf(
                "henry-v" to Play("Henry V", "history"),
                "as-like" to Play("As You Like It", "pastoral")
            )
        )
        val performanceList = PerformanceList(
            listOf(
                Performance("henry-v", 53),
                Performance("as-like", 55)
            )
        )
        val invoice = Invoice("BigCo", performanceList)
        val statementPrinter = StatementPrinter()
        
        assertThrows(Error::class.java) { statementPrinter.print(invoice, catalog) }
    }
}
