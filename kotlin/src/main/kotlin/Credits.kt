data class Credits(val credits: Int) {
    fun add(other: Credits): Credits {
        return Credits(credits + other.credits)
    }

    override fun toString(): String {
        return credits.toString()
    }
}
