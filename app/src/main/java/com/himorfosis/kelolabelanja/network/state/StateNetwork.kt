package com.himorfosis.kelolabelanja.network.state

sealed class StateNetwork<out T> {
    class OnSuccess<T>(var data: T) : StateNetwork<T>()
    class OnError(var status: Int, var error: String, var message: String) : StateNetwork<Nothing>()
    class OnFailure(var error: Throwable) : StateNetwork<Nothing>()
}