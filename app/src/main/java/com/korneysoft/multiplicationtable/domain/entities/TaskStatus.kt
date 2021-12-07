package com.korneysoft.multiplicationtable.domain.entities

enum class TaskStatus {
    NOT_STUDIED,
    POORLY_STUDIED,
    MIDDLE_STUDIED,
    GOOD_STUDIED;

    fun getStatus(responseTimeInMs: Long): TaskStatus {
        return when {
            (responseTimeInMs >= ResponseTime.RESPONSE_TIME_NOT_STUDIED) -> {
                NOT_STUDIED
            }
            (responseTimeInMs >= ResponseTime.RESPONSE_TIME_POORLY_STUDIED) -> {
                POORLY_STUDIED
            }
            (responseTimeInMs >= ResponseTime.RESPONSE_TIME_MIDDLE_STUDIED) -> {
                MIDDLE_STUDIED
            }
            else -> {
                GOOD_STUDIED
            }
        }
    }
}
