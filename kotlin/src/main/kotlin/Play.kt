import kotlin.math.floor
import kotlin.math.max

abstract class Play(open val name: String, val type: String) {
    abstract fun amount(audience: Int): Amount

    abstract fun credits(audience: Int): Credits
}

class Tragedy(override val name: String) : Play(name, "tragedy") {
    override fun amount(audience: Int): Amount {
        var performanceAmount = Amount(40000)
        performanceAmount = performanceAmount.add(extraAmountByAudience(audience))
        return performanceAmount.add(extraAmountByGenre())
    }

    override fun credits(audience: Int): Credits {
        var performanceCredits = Credits(max(audience - 30, 0))
        performanceCredits = performanceCredits.add(creditsByGenre())
        return performanceCredits
    }

    private fun extraAmountByAudience(audience: Int): Amount {
        if (audience > 30) {
            return Amount(1000 * (audience - 30))
        }
        return Amount(0)
    }

    private fun extraAmountByGenre(): Amount {
        return Amount(0)
    }

    private fun creditsByGenre(): Credits {
        return Credits(0)
    }
}

class Comedy(override val name: String) : Play(name, "comedy") {
    override fun amount(audience: Int): Amount {
        var performanceAmount = Amount(30000)
        performanceAmount = performanceAmount.add(extraAmountByAudience(audience))
        return performanceAmount.add(extraAmountByGenre(audience))
    }

    override fun credits(audience: Int): Credits {
        var performanceCredits = Credits(max(audience - 30, 0))
        performanceCredits = performanceCredits.add(creditsByGenre(audience))
        return performanceCredits
    }

    private fun extraAmountByAudience(audience: Int): Amount {
        if (audience > 20) {
            return Amount(10000 + 500 * (audience - 20))
        }
        return Amount(0)
    }

    private fun extraAmountByGenre(audience: Int) = Amount(300 * audience)

    private fun creditsByGenre(audience: Int): Credits {
        return Credits(floor((audience / 5).toDouble()).toInt())
    }
}


