import memento.CreditsMemento
import memento.State

data class Credits(val credits: Int): State<CreditsMemento> {
    fun add(other: Credits): Credits {
        return Credits(credits + other.credits)
    }

    override fun state(): CreditsMemento {
        return CreditsMemento(credits)
    }

    override fun toString(): String {
        return credits.toString()
    }
}
