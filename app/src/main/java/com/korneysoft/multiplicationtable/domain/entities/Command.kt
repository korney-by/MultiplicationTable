package com.korneysoft.multiplicationtable.domain.entities

enum class Command {

    PROCESS_START,
    PROCESS_STOP,
    PROCESS_FINISH,

    TASK_START,
    TASK_STOP,
    TASK_FINISH;

    object Pair

    fun getNewCommandPair(command: Command, value: Long?): Pair<Command, Long?> {
        return Pair(command, value)
    }
}
