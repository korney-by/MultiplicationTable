package com.korneysoft.multiplicationtable.domain.entities

object ResponseTime {
    // time for response in seconds
    const val RESPONSE_TIME_GOOD_STUDIED = 2000L
    const val RESPONSE_TIME_MIDDLE_STUDIED = 5000L
    const val RESPONSE_TIME_POORLY_STUDIED = 7500L
    const val RESPONSE_TIME_NOT_STUDIED = 10000L
    const val RESPONSE_TIME_MAX = 12000L

    fun getFromTaskRating(rating: TaskRating): Long {
        return when (rating) {
            TaskRating.GOOD_STUDIED -> RESPONSE_TIME_GOOD_STUDIED
            TaskRating.MIDDLE_STUDIED -> RESPONSE_TIME_MIDDLE_STUDIED
            TaskRating.POORLY_STUDIED -> RESPONSE_TIME_POORLY_STUDIED
            TaskRating.NOT_STUDIED -> RESPONSE_TIME_NOT_STUDIED
        }
    }
}
