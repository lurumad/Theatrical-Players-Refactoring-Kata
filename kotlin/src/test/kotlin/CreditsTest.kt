import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class CreditsTest {
    @Test
    fun `should be able to accumulate credits`() {
        val credits = Credits(10)
        credits.add(Credits(5)) `should be equal to` Credits(15)
    }
}