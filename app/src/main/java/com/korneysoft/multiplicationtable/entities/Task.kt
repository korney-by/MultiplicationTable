package com.korneysoft.multiplicationtable.entities

data class Task(
    val parapeter1: Int,
    val parapeter2: Int,
    val result: Int,
    val signOperation: Char,
    var status: TaskStatus = TaskStatus.NOT_STUDIED
) {
    fun getId(): String {
        return if (parapeter1 <= parapeter2) {
            "${parapeter1}x$parapeter2"
        } else {
            "${parapeter2}x$parapeter1"
        }
    }

    fun getIdWithResult(): String {
        return getId() + "="
    }
}
