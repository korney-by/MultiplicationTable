package com.korneysoft.multiplicationtable.domain.entities

enum class TaskRating(val value: Int) {
    NOT_STUDIED(10),
    POORLY_STUDIED(20),
    MIDDLE_STUDIED(30),
    GOOD_STUDIED(40);

    companion object {
        fun getRatingFromResponseTime(responseTimeInMs: Long): TaskRating {
            return when {
                (responseTimeInMs > ResponseTime.RESPONSE_TIME_POORLY_STUDIED) -> {
                    POORLY_STUDIED
                }
                (responseTimeInMs > ResponseTime.RESPONSE_TIME_MIDDLE_STUDIED) -> {
                    POORLY_STUDIED
                }
                (responseTimeInMs > ResponseTime.RESPONSE_TIME_GOOD_STUDIED) -> {
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
