package com.himorfosis.kelolabelanja.database.db

enum class StateDB(val status: String) {

    onSuccess("SUCCESSS"),
    onError("ERROR"),
    onEmpty("EMPETY")

}