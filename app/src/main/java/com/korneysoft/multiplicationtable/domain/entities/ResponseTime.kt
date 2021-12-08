package com.korneysoft.multiplicationtable.domain.entities

object ResponseTime {
    // time for response in seconds
    const val RESPONSE_TIME_GOOD_STUDIED = 3000L
    const val RESPONSE_TIME_MIDDLE_STUDIED = 6000L
    const val RESPONSE_TIME_POORLY_STUDIED = 90000L
    const val RESPONSE_TIME_NOT_STUDIED = 12000L
    const val RESPONSE_TIME_MAX = 15000L

    fun getFromTaskStatus(status: TaskStatus): Long {
        return when (status) {
            TaskStatus.GOOD_STUDIED -> RESPONSE_TIME_GOOD_STUDIED
            TaskStatus.MIDDLE_STUDIED -> RESPONSE_TIME_MIDDLE_STUDIED
            TaskStatus.POORLY_STUDIED -> RESPONSE_TIME_POORLY_STUDIED
            TaskStatus.NOT_STUDIED -> RESPONSE_TIME_NOT_STUDIED
        }
    }
}
