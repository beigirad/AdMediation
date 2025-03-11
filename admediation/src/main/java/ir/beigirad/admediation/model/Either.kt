package ir.beigirad.admediation.model

sealed interface Either<out T> {
    data class Success<T>(val data: T) : Either<T>
    data class Failure(val error: String) : Either<Nothing>
}