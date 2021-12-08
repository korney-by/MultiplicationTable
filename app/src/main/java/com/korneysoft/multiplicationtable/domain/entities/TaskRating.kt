package com.korneysoft.multiplicationtable.domain.entities

enum class TaskRating {
    NOT_STUDIED,
    POORLY_STUDIED,
    MIDDLE_STUDIED,
    GOOD_STUDIED;

    private val value: Int
        get() {
            return when (this) {
                NOT_STUDIED -> 0
                POORLY_STUDIED -> 1
                MIDDLE_STUDIED -> 2
                GOOD_STUDIED -> 3
            }
        }

    companion object {
        fun getRatingFromResponseTime(responseTimeInMs: Long): TaskRating {
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

        fun getBetterRating(rating1: TaskRating, rating2: TaskRating): TaskRating {
            return if (rating1.value >= rating2.value) {
                rating1
            } else {
                rating2
            }
        }
    }
}
