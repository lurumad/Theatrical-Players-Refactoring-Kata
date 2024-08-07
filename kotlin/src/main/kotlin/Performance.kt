import memento.PerformanceMemento
import memento.State
import kotlin.math.floor
import kotlin.math.max

data class Performance(
    private val play: Play,
    private val audience: Int
): State<PerformanceMemento> {

    fun credits(): Credits {
        return play.credits(audience)
    }

    fun amount(): Amount {
        return play.amount(audience)
    }

    override fun state(): PerformanceMemento {
        return PerformanceMemento(play.name, amount().state().amount, audience)
    }
}
