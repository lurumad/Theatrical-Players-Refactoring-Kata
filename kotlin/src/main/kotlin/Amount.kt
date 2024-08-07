import memento.AmountMemento
import memento.State

data class Amount(private val amount: Int = 0): State<AmountMemento> {
    fun add(another: Amount): Amount {
        return Amount(amount + another.amount)
    }

    private fun usd(): Int {
        return amount / 100
    }

    override fun state(): AmountMemento {
        return AmountMemento(usd())
    }
}
