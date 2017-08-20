package nl.cc

data class UpdateField<T>(val value: T? = null, val ignored: Boolean = false) {

    fun get(): T = value ?: throw IllegalStateException("value cannot be null.")
    fun getOrNull(): T? = value

    companion object {
        fun <T> ignore() = UpdateField<T>(ignored = true)
        fun <T> setNull() = UpdateField<T>()
        fun <T> of(value: T) = UpdateField<T>(value = value)
    }
}