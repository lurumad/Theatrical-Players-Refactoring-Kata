package memento

interface Memento {
}

interface State<T: Memento> {
    fun state(): T
}