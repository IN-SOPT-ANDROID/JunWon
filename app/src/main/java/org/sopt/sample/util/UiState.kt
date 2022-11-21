package org.sopt.sample.util

sealed class UiState<out T> {
    object Empty : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<T>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val error: Throwable
    ) : UiState<Nothing>()
}

inline fun <reified T : Any> UiState<T>.onSuccess(action: (data: T) -> Unit): UiState<T> {
    if (this is UiState.Success<T>) {
        action(data)
    }
    return this
}

inline fun <reified T : Any> UiState<T>.onFailed(action: (exception: Throwable) -> Unit): UiState<T> {
    if (this is UiState.Error) {
        action(error)
    }
    return this
}

inline fun <reified T : Any> UiState<T>.onLoading(action: () -> Unit): UiState<T> {
    if (this is UiState.Loading) {
        action()
    }
    return this
}

inline fun <reified T : Any> UiState<T>.onEmpty(action: () -> Unit): UiState<T> {
    if (this is UiState.Empty) {
        action()
    }
    return this
}
