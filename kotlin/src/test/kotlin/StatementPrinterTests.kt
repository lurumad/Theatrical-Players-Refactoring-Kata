
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.approvaltests.Approvals.verify

class StatementPrinterTests {

    @Test
    internal fun exampleStatement() {
        val performanceList = PerformanceList(
            listOf(
                Performance(Tragedy("Hamlet"), 55),
                Performance(Comedy("As You Like It"), 35),
                Performance(Tragedy("Othello"), 40)
            )
        )
        val invoice = Invoice("BigCo", performanceList)
        val statementPrinter = StatementPrinter()

        val result = statementPrinter.print(invoice)

        verify(result)
    }
}
