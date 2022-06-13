package model

import event.input.InputEvent

interface Client {
    fun send(data: String)
    var eventHandler: ((InputEvent) -> Unit)?
}
