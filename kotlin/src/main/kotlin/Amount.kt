data class Amount(val amount: Int = 0) {
    fun add(another: Amount): Amount {
        return Amount(amount + another.amount)
    }
}
