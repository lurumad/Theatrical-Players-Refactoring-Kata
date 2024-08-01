package memento

data class InvoiceMemento(
    val customer: String,
    val amount: Int,
    val credits: Int,
    val performances: List<PerformanceMemento>
) : Memento

data class PerformanceMemento(
    val playName: String,
    val amount: Int,
    val audience: Int
) : Memento

data class AmountMemento(val amount: Int) : Memento

data class CreditsMemento(val credits: Int) : Memento

