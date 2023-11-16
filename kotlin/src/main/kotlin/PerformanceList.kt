class PerformanceList(private val performances: List<Performance>) : Iterable<Performance> {
    override fun iterator(): Iterator<Performance> {
        return performances.iterator()
    }
}
