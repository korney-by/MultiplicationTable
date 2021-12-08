package com.korneysoft.multiplicationtable.domain.entities

enum class Command {
    PROCESS_START,
    PROCESS_STOP,
    PROCESS_FINISH,
    PROCESS_CHANGE_TASK,

    TASK_START,
    TASK_STOP,
    TASK_FINISH;

    companion object {
        fun getCommandPair(command: Command, value: Int?=null): Pair<Command, Int?> {
            return Pair(command, value)
        }
    }
}
