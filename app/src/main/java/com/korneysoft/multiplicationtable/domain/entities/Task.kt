package com.korneysoft.multiplicationtable.domain.entities

data class Task(
    val parameter1: Int,
    val parameter2: Int,
    val result: Int,
    val signOperation: Char,
    //var rating: TaskRating = TaskRating.NOT_STUDIED
) {
    fun getId(): String {
        return if (parameter1 <= parameter2) {
            "${parameter1}$signOperation$parameter2"
        } else {
            "${parameter2}$signOperation$parameter1"
        }
    }

    fun getIdWithResult(): String {
        return getId() + "="
    }

    override fun toString(): String {
        return if (parameter1 <= parameter2) {
            "${parameter1} • $parameter2 ="
        } else {
            "${parameter2} • $parameter1 ="
        }
    }

    fun toStringWithResult(): String {
        return toString() + " $result"
    }


}
