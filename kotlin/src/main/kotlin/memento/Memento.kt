package memento

interface Memento {
}

interface State<T: Memento> {
    fun save(): T
}