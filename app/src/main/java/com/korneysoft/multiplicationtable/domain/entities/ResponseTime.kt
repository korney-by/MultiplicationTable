package com.korneysoft.multiplicationtable.domain.entities

object ResponseTime {
    // time for response in seconds
    private const val RESPONSE_TIME_STUDIED = 2
    private const val RESPONSE_TIME_MIDDLE_STUDIED = 4
    private const val RESPONSE_TIME_POORLY_STUDIED = 7
    private const val RESPONSE_TIME_NOT_STUDIED = 10

    fun getFromTaskStatus(status: TaskStatus): Int {
        return when (status) {
            TaskStatus.STUDIED -> RESPONSE_TIME_STUDIED
            TaskStatus.GOOD_STUDIED -> RESPONSE_TIME_MIDDLE_STUDIED
            TaskStatus.POORLY_STUDIED -> RESPONSE_TIME_POORLY_STUDIED
            TaskStatus.NOT_STUDIED -> RESPONSE_TIME_NOT_STUDIED
        }
    }
}
