package com.sharedmobility.api.global.types

import java.time.Duration

enum class TicketType(
    duration: Int
) {
    SAMPLE(12)
    ;

    val duration: Duration = Duration.ofDays(duration.toLong())
}
