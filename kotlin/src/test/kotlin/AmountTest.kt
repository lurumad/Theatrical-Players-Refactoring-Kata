import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class AmountTest {
    @Test
    fun `should be able to accumulate amounts`() {
        val amount = Amount(1000)
        amount.add(Amount(500)) `should be equal to` Amount(1500)
    }
}