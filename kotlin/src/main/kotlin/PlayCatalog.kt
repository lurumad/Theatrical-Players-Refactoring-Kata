class PlayCatalog(private val plays: Map<String, Play>) {
    fun playBy(id: String): Play {
        return plays.getValue(id)
    }
}
